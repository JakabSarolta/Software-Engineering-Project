package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;

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

            if(canBeMeasured(device, currentTime)){
                double measurement = DataCollector.getDataCollector().takeMeasurementForDevice(device);
                double min = processor.getMinValueForDevice(device);
                double max = processor.getMaxValueForDevice(device);

                if(shouldRise.containsKey(device)){
                    if((shouldRise.get(device) && measurement >= max) || (!shouldRise.get(device) && measurement <= min)){
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
                if(shouldRise.get(device)){
                    EnvironmentSimulator.getEnvironmentSimulator().setActuatorStrength(device, EnvironmentControlPanel.getEnvironmentControlPanel().getActuatorStrength());
                }else{
                    EnvironmentSimulator.getEnvironmentSimulator().setActuatorStrength(device, (-1) * EnvironmentControlPanel.getEnvironmentControlPanel().getActuatorStrength());
                }
            }
        }

        devicesToBeBalanced.removeAll(devicesToBeRemoved);
    }

    private boolean canBeMeasured(EnvironmentDeviceTypes device, long currentTime) {
        return (!timeWhenLastMeasured.containsKey(device) ||
                (currentTime - timeWhenLastMeasured.get(device)) >=  processor.getBalancingCheckIntervalForDevice(device));
    }
}
