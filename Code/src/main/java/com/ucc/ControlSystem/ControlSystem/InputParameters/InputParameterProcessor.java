package com.ucc.ControlSystem.ControlSystem.InputParameters;

import com.ucc.ControlSystem.ControlSystem.JDBC.HSQLQueries;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputParameterProcessor {

    private static InputParameterProcessor inputParameterProcessor = null;
    private static final int LIGHT_TIME_MIN = Integer.parseInt(SystemConfigurationReader.getSystemConfigurationReader()
            .readParam(SystemConfigParameters.LIGHT_TIME_MIN));
    private static final int LIGHT_TIME_MAX = Integer.parseInt(SystemConfigurationReader.getSystemConfigurationReader()
            .readParam(SystemConfigParameters.LIGHT_TIME_MAX));

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

    // will get all the input parameters from the DB
    public List<InputParameter> getParameters(){
        return new ArrayList<>();
    }

    public void updateParameters(List<Map<String,Object>> parameterList){
        // update the system parameters
    }

    public static InputParameterProcessor getInputParameterProcessor(){
        if(inputParameterProcessor == null){
            inputParameterProcessor = new InputParameterProcessor();
        }
        return inputParameterProcessor;
    }

    public void updateEnvironmentPropertyParameter(EnvironmentPropertyParameter parameter){
        if(parameter.isValid()){
            environmentPropertyParameterMap.put(parameter.getPropertyType(),parameter);
        }
    }

    public void updateMeasurementIntervalParameter(MeasurementIntervalParameter parameter){
        if(parameter.isValid()){
            measurementIntervalParameterMap.put(parameter.getPropertyType(),parameter);
        }
    }

    public void updateOtherParameter(OtherParameter parameter){
        if(parameter.isValid()){
            otherParameterMap.put(parameter.getType(),parameter);
        }
    }

    private Map<OtherParameters, OtherParameter> initializeOtherParameterMap() {
        Map<OtherParameters, OtherParameter> otherParametersMap = new HashMap<>();
        for(OtherParameters p : OtherParameters.values()){

            long min_valid = Long.parseLong(SystemConfigurationReader.getSystemConfigurationReader()
                    .readParam(SystemConfigParameters.valueOf(p+"_MIN")));
            long max_valid = Long.parseLong(SystemConfigurationReader.getSystemConfigurationReader()
                    .readParam(SystemConfigParameters.valueOf(p+"_MAX")));

            otherParametersMap.put(p,new OtherParameter(p,0,min_valid,max_valid));
        }
        return otherParametersMap;
    }

    private Map<EnvironmentDeviceTypes,EnvironmentPropertyParameter> initializeEnvironmentPropertyParameterList() {
        Map<EnvironmentDeviceTypes,EnvironmentPropertyParameter> environmentPropertyParameterList = new HashMap<>();
        for(EnvironmentDeviceTypes e : EnvironmentDeviceTypes.values()){

            // todo: if values in DB populate from DB, else put default 0 value in them
            HSQLQueries.getHSQLQueries().getEnvironmentPropertyParametersWithType(EnvironmentDeviceTypes.AIR_TEMPERATURE)
                    .forEach(epp-> System.out.println(epp.getId()));

            // get the valid value range from config file
            SystemConfigurationReader systemConfigurationReader = SystemConfigurationReader.getSystemConfigurationReader();
            double valid_min = Double.parseDouble(systemConfigurationReader.readParam(SystemConfigParameters.valueOf(e+"_VALID_MIN")));
            double valid_max = Double.parseDouble(systemConfigurationReader.readParam(SystemConfigParameters.valueOf(e+"_VALID_MAX")));
            double precision = Double.parseDouble(systemConfigurationReader.readParam(SystemConfigParameters.valueOf(e+"_VALID_MAX")));

            environmentPropertyParameterList.put(e,new EnvironmentPropertyParameter(valid_min, valid_max, precision, e));
        }
        return environmentPropertyParameterList;
    }

    private Map<EnvironmentDeviceTypes,MeasurementIntervalParameter> initializeMeasurementIntervalParameterList() {
        Map<EnvironmentDeviceTypes,MeasurementIntervalParameter> measurementIntervalParameterList = new HashMap<>();
        for(EnvironmentDeviceTypes e : EnvironmentDeviceTypes.values()){
            measurementIntervalParameterList.put(e,new MeasurementIntervalParameter(e));
        }
        return measurementIntervalParameterList;
    }

    private boolean isLightTimeValid(int lightTime){
        return lightTime >= LIGHT_TIME_MIN && lightTime <= LIGHT_TIME_MAX;
    }
}
