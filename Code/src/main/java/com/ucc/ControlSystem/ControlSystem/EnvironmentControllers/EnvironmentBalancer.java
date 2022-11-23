package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
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
    private InputParameterProcessor processor;

    private EnvironmentBalancer(){
        timeWhenLastMeasured = new HashMap<>();
        processor = InputParameterProcessor.getInputParameterProcessor();
        shouldRise = new HashMap<>();
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
    public void balanceEnvironment(long currentTime, List<EnvironmentDeviceTypes> devicesToBeBalanced){
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
                    }
                }else{
                    if(measurement < min){
                        shouldRise.put(device,true);
                    }else if(measurement > max){
                        shouldRise.put(device,false);
                    }
                }
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
