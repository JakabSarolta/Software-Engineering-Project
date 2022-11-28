package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.ControlSystem.Reporting.Measurement;
import com.ucc.ControlSystem.GUI.AdminControlPanel;
import com.ucc.ControlSystem.GUI.Alert;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class corresponding to the balancing state
 */
public class EnvironmentBalancer {

    private static EnvironmentBalancer environmentBalancer = null;

    private Map<EnvironmentDeviceTypes, Long> timeWhenLastMeasured;
    private Map<EnvironmentDeviceTypes, Boolean> shouldRise;
    private Map<EnvironmentDeviceTypes, Double> lastMeasuredValue;
    private Map<EnvironmentDeviceTypes, Double> currentValues;
    private InputParameterProcessor processor;

    private EnvironmentBalancer(){
        timeWhenLastMeasured = new HashMap<>();
        processor = InputParameterProcessor.getInputParameterProcessor();
        shouldRise = new HashMap<>();
        lastMeasuredValue = new HashMap<>();
        currentValues = new HashMap<>();
    }

    public static EnvironmentBalancer getEnvironmentBalancer() {
        if(environmentBalancer == null){
            environmentBalancer = new EnvironmentBalancer();
        }
        return environmentBalancer;
    }

    /**
     * Balances the parameters that are given by turning on and off the actuator. It looks at the values
     * at time intervals set in the intervalBalancingState field of the parameter. It tries to bring the values
     * to the average of the min and max range. If a parameter is balanced it removes it from the list.
     * @param currentTime the current time
     * @param devicesToBeBalanced the updated list of devices that should be balanced
     */
    public States balanceEnvironment(long currentTime, List<EnvironmentDeviceTypes> devicesToBeBalanced){
        List<EnvironmentDeviceTypes> devicesToBeRemoved = new ArrayList<>();
        for(EnvironmentDeviceTypes device : devicesToBeBalanced){

            if(shouldBeCheckedNow(device, currentTime)){
                double measurement = DataCollector.getDataCollector().takeMeasurementForDevice(device);
                double min = processor.getMinValueForDevice(device);
                double max = processor.getMaxValueForDevice(device);

                double avg = (min + max) / 2.0;

                if(shouldRise.containsKey(device)){
                    if((shouldRise.get(device) && measurement >= avg) || (!shouldRise.get(device) && measurement <= avg)){
                        shouldRise.remove(device);
                        devicesToBeRemoved.add(device);
                        DataCollector.getDataCollector().setActuatorCurrentStrength(device,0);
                        AdminControlPanel.getAdminControlPanel().getActuatorState().setText("OFF");
                    }

                    if((shouldRise.containsKey(device)) && ((shouldRise.get(device) && measurement <= lastMeasuredValue.get(device))
                    || (!shouldRise.get(device) && measurement >= lastMeasuredValue.get(device)))){
                        Alert.alert("Reason: " + device + " rising: " + !shouldRise.get(device));
                        new Measurement(device,measurement,States.ALERTED,currentTime).saveMeasurement();
                        return States.ALERTED;
                    }

                    lastMeasuredValue.put(device,currentValues.get(device));
                }else{
                    if(measurement < min){
                        shouldRise.put(device,true);
                        AdminControlPanel.getAdminControlPanel().getActuatorState().setText("ON");
                    }else if(measurement > max){
                        shouldRise.put(device,false);
                        AdminControlPanel.getAdminControlPanel().getActuatorState().setText("ON");
                    }
                    lastMeasuredValue.put(device,measurement);
                }
                currentValues.put(device,measurement);
                new Measurement(device, measurement,States.BALANCING,currentTime).saveMeasurement();
                timeWhenLastMeasured.put(device,currentTime);
            }

            if(shouldRise.containsKey(device)){
                if(shouldRise.get(device)){ // value is True, i.e. the actuator should increase temp, lvl etc.
                    DataCollector.getDataCollector().setActuatorCurrentStrength(device,
                            DataCollector.getDataCollector().getActuatorSetStrength(device));
                }else{ // value is False, actuator should decrease temp, lvl etc.
                    DataCollector.getDataCollector().setActuatorCurrentStrength(device,
                            (-1) * DataCollector.getDataCollector().getActuatorSetStrength(device));
                }
            }
        }

        devicesToBeBalanced.removeAll(devicesToBeRemoved);
        return States.BALANCING;
    }

    /**
     * Decides whether a parameter should be checked at the current time
     * @param device the parameter type
     * @param currentTime the current time
     * @return true if it wasn't checked before, or if enough time elapsed from the last check
     */
    private boolean shouldBeCheckedNow(EnvironmentDeviceTypes device, long currentTime) {
        return (!timeWhenLastMeasured.containsKey(device) ||
                (currentTime - timeWhenLastMeasured.get(device)) >=  processor.getBalancingCheckIntervalForDevice(device));
    }

    public boolean isActuatorOn(EnvironmentDeviceTypes device){
        return  shouldRise.containsKey(device);
    }


}
