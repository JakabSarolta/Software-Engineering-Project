package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.JDBC.HSQLQueries;
import com.ucc.ControlSystem.ControlSystem.Reporting.Measurement;
import com.ucc.ControlSystem.GUI.AdminControlPanel;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that controls the EnvironmentController.
 */
public class Controller{
    private static Controller controller = null;

    private States currentState;
    private final Sentinel sentinel;
    private final EnvironmentBalancer environmentBalancer;

    private List<EnvironmentDeviceTypes> parametersToBeBalanced;

    private List<Measurement> measurementList;

    private static final int MEASUREMENT_PERSIST_TIME_INTERVAL = 3600;

    private Controller(){
        currentState = States.BALANCED;
        parametersToBeBalanced = new ArrayList<>();
        sentinel = Sentinel.getSentinel();
        environmentBalancer = EnvironmentBalancer.getEnvironmentBalancer();
        HSQLQueries.getHSQLQueries().emptyTable(Measurement.class);
        measurementList = new ArrayList<>();
    }

    public static Controller getController(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }

    /**
     * Called when a second passed. If the system is not in a special state, and if the crops are not
     * ready to be harvested yet, it performs check for parameters in the balanced state and for
     * devices in the balancing state.
     * @param currentTime the current time in seconds (salad lifetime)
     */
    public void timePassed(long currentTime){
        if(currentState != States.GROWTH_ENDED  && currentState != States.ALERTED) {
            if (sentinel.isGrowthTimeDue(currentTime)) {
                currentState = States.GROWTH_ENDED;
            } else {
                sentinel.checkPeriodically(currentTime,parametersToBeBalanced);
            }

            currentState = environmentBalancer.balanceEnvironment(currentTime, parametersToBeBalanced);

            AdminControlPanel.getAdminControlPanel().getCurrentTime().setText(convertSeconds(currentTime)+"");

            Map<EnvironmentDeviceTypes, List<JLabel>> deviceToLabelMap = AdminControlPanel.deviceToLabelMap;
            for(EnvironmentDeviceTypes device : deviceToLabelMap.keySet()){
                deviceToLabelMap.get(device).get(0).setText(Math.round(DataCollector.getDataCollector().getSensorValue(device) * 10) / 10.0+"");
            }
        }else if(currentState == States.ALERTED){
            currentState = States.BALANCED;
        }else{
            com.ucc.ControlSystem.EnvironmentSimulator.Controller.stopSimulation();
        }

        if(currentTime % MEASUREMENT_PERSIST_TIME_INTERVAL == 0){
            persistMeasurements(measurementList);
        }
    }

    private void persistMeasurements(List<Measurement> measurementList) {
        measurementList.forEach(Measurement::saveMeasurement);
        measurementList.removeAll(measurementList);
    }

    /**
     * Converts the time from seconds to a legible format.
     * @param seconds the time in seconds
     * @return the legible string
     */
    private String convertSeconds(long seconds) {
        if(seconds >= 86400){
            return seconds/86400 + " days " + (seconds - (seconds / 86400) * 86400) / 3600 + " hours";
        }else if(seconds >= 3600){
            return seconds/3600 + " hours " + (seconds  - (seconds / 3600 ) * 3600)  / 60 + " minutes";
        }else if(seconds >= 60){
            return seconds/60 + " minutes " + (seconds - (seconds / 60) * 60)  + " seconds";
        }else {
            return seconds + " seconds";
        }
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }
}
