package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.ControlSystem.Reporting.Measurement;
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

    public States balanceEnvironment(long currentTime, List<EnvironmentDeviceTypes> devicesToBeBalanced){
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

                    if((shouldRise.get(device) && measurement <= lastMeasuredValue.get(device))
                    || (!shouldRise.get(device) && measurement >= lastMeasuredValue.get(device))){
                        System.out.printf("ALERTED! Reason: " + device + " rising: " + !shouldRise.get(device));
                        new Measurement(device,measurement,States.ALERTED).saveMeasurement();
                        return States.ALERTED;
                    }

                    lastMeasuredValue.put(device,currentValues.get(device));
                }else{
                    if(measurement < min){
                        shouldRise.put(device,true);
                    }else if(measurement > max){
                        shouldRise.put(device,false);
                    }
                    lastMeasuredValue.put(device,measurement);
                }
                currentValues.put(device,measurement);
                new Measurement(device, measurement,States.BALANCING).saveMeasurement();
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

        return States.BALANCING;
    }

    private boolean canBeMeasured(EnvironmentDeviceTypes device, long currentTime) {
        return (!timeWhenLastMeasured.containsKey(device) ||
                (currentTime - timeWhenLastMeasured.get(device)) >=  processor.getBalancingCheckIntervalForDevice(device));
    }
}
