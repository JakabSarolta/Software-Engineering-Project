package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                        EnvironmentSimulator.getEnvironmentSimulator().setActuatorStrength(device,0);
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
                    EnvironmentSimulator.getEnvironmentSimulator().setActuatorStrength(device,
                            EnvironmentControlPanel.getEnvironmentControlPanel().getActuatorStrength());
                }else{ // value is False, actuator should decrease temp, lvl etc.
                    EnvironmentSimulator.getEnvironmentSimulator().setActuatorStrength(device,
                            (-1) * EnvironmentControlPanel.getEnvironmentControlPanel().getActuatorStrength());
                }
            }
        }

        devicesToBeBalanced.removeAll(devicesToBeRemoved);
    }

    private boolean shouldBeCheckedNow(EnvironmentDeviceTypes device, long currentTime) {
        return (!timeWhenLastMeasured.containsKey(device) ||
                (currentTime - timeWhenLastMeasured.get(device)) >=  processor.getBalancingCheckIntervalForDevice(device));
    }

    public boolean isActuatorOn(EnvironmentDeviceTypes device){
        return  shouldRise.containsKey(device);
    }


}
