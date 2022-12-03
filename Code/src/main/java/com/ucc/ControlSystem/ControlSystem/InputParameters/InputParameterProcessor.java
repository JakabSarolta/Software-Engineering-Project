package com.ucc.ControlSystem.ControlSystem.InputParameters;

import com.ucc.ControlSystem.ControlSystem.JDBC.ConnectionFactory;
import com.ucc.ControlSystem.ControlSystem.JDBC.HSQLQueries;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.GUI.Alert;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class for processing the input parameters.
 */
public class InputParameterProcessor {

    private static InputParameterProcessor inputParameterProcessor = null;
    private static final int LIGHT_TIME_MIN = Integer.parseInt(SystemConfigurationReader.getSystemConfigurationReader()
            .readParam(SystemConfigParameters.LIGHT_OFF_TIME_MIN));
    private static final int LIGHT_TIME_MAX = Integer.parseInt(SystemConfigurationReader.getSystemConfigurationReader()
            .readParam(SystemConfigParameters.LIGHT_ON_TIME_MAX));

    private Map<EnvironmentDeviceTypes,EnvironmentPropertyParameter> environmentPropertyParameterMap;
    private Map<EnvironmentDeviceTypes,MeasurementIntervalParameter> measurementIntervalParameterMap;

    private Map<OtherParameters, OtherParameter> otherParameterMap;

//    private double NPKRatio;

    private InputParameterProcessor(){
        environmentPropertyParameterMap = initializeEnvironmentPropertyParameterList();
        measurementIntervalParameterMap = initializeMeasurementIntervalParameterList();
        otherParameterMap = initializeOtherParameterMap();

//        NPKRatio = 0;
    }

    public static InputParameterProcessor getInputParameterProcessor(){
        if(inputParameterProcessor == null){
            inputParameterProcessor = new InputParameterProcessor();
        }
        return inputParameterProcessor;
    }

    /**
     * will get all the input parameters from the DB
     */
    public List<InputParameter> getParameters(){
        List<InputParameter> allParams = new ArrayList<>();
        allParams.addAll(environmentPropertyParameterMap.values());
        allParams.addAll(measurementIntervalParameterMap.values());
        allParams.addAll(otherParameterMap.values());
        return allParams;
    }

    public double getMinValueForDevice(EnvironmentDeviceTypes deviceTypes){
        return environmentPropertyParameterMap.get(deviceTypes).getMin();
    }

    public double getMaxValueForDevice(EnvironmentDeviceTypes deviceTypes){
        return environmentPropertyParameterMap.get(deviceTypes).getMax();
    }

    public long getBalancedCheckIntervalForDevice(EnvironmentDeviceTypes deviceType){
        return measurementIntervalParameterMap.get(deviceType).getIntervalBalancedState();
    }

    public long getBalancingCheckIntervalForDevice(EnvironmentDeviceTypes deviceType){
        return measurementIntervalParameterMap.get(deviceType).getIntervalBalancingState();
    }

    public EnvironmentPropertyParameter getEnvironmentPropertyForDevice(EnvironmentDeviceTypes deviceType){
        return environmentPropertyParameterMap.get(deviceType);
    }

    public OtherParameter getTotalGrowthTime(){
        return otherParameterMap.get(OtherParameters.GROWTH_TIME);
    }

    /**
     * Updates an entity that holds the ranges of a parameter if the new values are valid
     * @param min the bottom of the range
     * @param max the top of the range
     * @param propertyType the parameter type
     */
    public void updateEnvironmentPropertyParameter(double min, double max,EnvironmentDeviceTypes propertyType){
        EnvironmentPropertyParameter clone = environmentPropertyParameterMap.get(propertyType).clone();
        clone.setMin(min);
        clone.setMax(max);

        if (clone.isValid()) {
            environmentPropertyParameterMap.put(propertyType, clone);
        }else{
            Alert.alert("Environment property parameter (min or max) invalid! \n " +
                    "Valid range: [" + clone.getValidMin() + "," + clone.getValidMax() + "]","Not valid parameter", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateMeasurementIntervalParameter(int intervalBalancedState, int intervalBalancingState, EnvironmentDeviceTypes propertyType){
        if(MeasurementIntervalParameter.isValid(intervalBalancedState,intervalBalancingState)){
            MeasurementIntervalParameter param = measurementIntervalParameterMap.get(propertyType);
            param.setIntervalBalancedState(intervalBalancedState);
            param.setIntervalBalancingState(intervalBalancingState);
        }else{
            Alert.alert("Measurement interval parameter (balanced or balancing) invalid! \n " +
                    "Valid range: [" + MeasurementIntervalParameter.MEASUREMENT_INTERVAL_MIN + ":" + MeasurementIntervalParameter.MEASUREMENT_INTERVAL_MAX + "]","Not valid parameter", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates an entity that holds a special parameter if the new value is valid
     * @param type the parameter type
     * @param value the new value
     */
    public void updateOtherParameter(OtherParameters type, long value){
        OtherParameter clone = otherParameterMap.get(type).clone();
        clone.setValue(value);
        if(clone.isValid()){
            otherParameterMap.put(type,clone);
        }else{
            Alert.alert("Growth parameter invalid! \n " +
                    "Valid range: [" + clone.getValidValueMin() + "," + clone.getValidValueMax() + "]","Not valid parameter", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initializes the otherParametersMap, if the database doesn't contain values for that parameter type
     * it sets a default value of 0.
     * @return the initialized map
     */
    private Map<OtherParameters, OtherParameter> initializeOtherParameterMap() {
        Map<OtherParameters, OtherParameter> otherParametersMap = new HashMap<>();
        for(OtherParameters p : OtherParameters.values()){

            OtherParameter param = null;
            long min_valid = Long.parseLong(SystemConfigurationReader.getSystemConfigurationReader()
                    .readParam(SystemConfigParameters.valueOf(p+"_MIN")));
            long max_valid = Long.parseLong(SystemConfigurationReader.getSystemConfigurationReader()
                    .readParam(SystemConfigParameters.valueOf(p+"_MAX")));

            Object dbEntries = HSQLQueries.getHSQLQueries().getParameterByType(OtherParameter.class,p);

            if(dbEntries == null){
                param = new OtherParameter(p,0,max_valid,min_valid);
            }else{
                param = (OtherParameter) dbEntries;
                param.setValidValueMax(max_valid);
                param.setValidValueMin(min_valid);
            }

            otherParametersMap.put(p,param);
        }
        return otherParametersMap;
    }

    /**
     * Initializes the environmentPropertyParameterList with values from the database. If the database does not
     * contain values for that parameter type if initializes them with 0.
     * @return the initialized map
     */
    private Map<EnvironmentDeviceTypes,EnvironmentPropertyParameter> initializeEnvironmentPropertyParameterList() {
        Map<EnvironmentDeviceTypes,EnvironmentPropertyParameter> environmentPropertyParameterList = new HashMap<>();
        for(EnvironmentDeviceTypes e : EnvironmentDeviceTypes.values()){

            EnvironmentPropertyParameter param = null;
            double valid_min = getSysConfValForParam(e+"_VALID_MIN");
            double valid_max = getSysConfValForParam(e+"_VALID_MAX");
            double precision = getSysConfValForParam(e+"_PRECISION");

            Object dbEntry = HSQLQueries.getHSQLQueries().getParameterByType(EnvironmentPropertyParameter.class,e);
            if(dbEntry == null){
                Session  s= ConnectionFactory.getConnectionFactory().getSessionFactory().openSession();
                s.beginTransaction();

                param = new EnvironmentPropertyParameter(valid_min, valid_max, precision, e);
                s.persist(param);

                s.getTransaction().commit();
                s.close();
            }else{
                // there is a unique constraint on the column type, so for sure only one will exist
                param =(EnvironmentPropertyParameter) dbEntry;
                param.setValidMin(valid_min);
                param.setValidMax(valid_max);
                param.setPrecision(precision);
            }

            environmentPropertyParameterList.put(e,param);
        }
        return environmentPropertyParameterList;
    }

    /**
     * Initializes the measurementIntervalList with values from the database, if it doesn't contain values for
     * that data type it initializes them with 0.
     * @return the initialized map
     */
    private Map<EnvironmentDeviceTypes,MeasurementIntervalParameter> initializeMeasurementIntervalParameterList() {
        Map<EnvironmentDeviceTypes,MeasurementIntervalParameter> measurementIntervalParameterList = new HashMap<>();

        MeasurementIntervalParameter param = null;

        for(EnvironmentDeviceTypes e : EnvironmentDeviceTypes.values()){

            Object dbEntry = HSQLQueries.getHSQLQueries().getParameterByType(MeasurementIntervalParameter.class,e);

            if(dbEntry == null){
                param = new MeasurementIntervalParameter(e);
            }else{
                param = (MeasurementIntervalParameter) dbEntry;
            }

            measurementIntervalParameterList.put(e,param);
        }
        return measurementIntervalParameterList;
    }

    /**
     * Saves all the parameters into the database
     */
    public void persistParameters(){
        SessionFactory sf = ConnectionFactory.getConnectionFactory().getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        for(EnvironmentDeviceTypes p : EnvironmentDeviceTypes.values()){
            s.merge(environmentPropertyParameterMap.get(p));
            s.merge(measurementIntervalParameterMap.get(p));
        }

        for(OtherParameters p : OtherParameters.values()){
            s.merge(otherParameterMap.get(p));
        }

        s.getTransaction().commit();
        s.close();
    }

    private boolean isLightTimeValid(int lightTime){
        return lightTime >= LIGHT_TIME_MIN && lightTime <= LIGHT_TIME_MAX;
    }

    private Double getSysConfValForParam(String paramName){
        return Double.parseDouble(SystemConfigurationReader.getSystemConfigurationReader().
                readParam(SystemConfigParameters.valueOf(paramName)));
    }
}
