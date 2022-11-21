package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.InputParameters.EnvironmentPropertyParameter;
import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.ControlSystem.Reporting.Measurement;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;

import java.util.ArrayList;
import java.util.List;

public class Sentinel {

    private static Sentinel sentinel;
    private InputParameterProcessor inputParameterProcessor;
    private DataCollector dataCollector;

    private Sentinel(){
        inputParameterProcessor = InputParameterProcessor.getInputParameterProcessor();
        dataCollector = DataCollector.getDataCollector();
    }

    public static Sentinel getSentinel(){
        if(sentinel == null){
           sentinel = new Sentinel();
        }
        return sentinel;
    }

    public List<EnvironmentDeviceTypes> checkPeriodically(long currentTime){
        List<EnvironmentDeviceTypes> parametersToBeBalances = new ArrayList<>();
        for(EnvironmentDeviceTypes deviceType : EnvironmentDeviceTypes.values()){
            long checkIntervalForDevice = inputParameterProcessor.getBalancedCheckIntervalForDevice(deviceType);

            if(currentTime % checkIntervalForDevice == 0){
                Double measurement = dataCollector.takeMeasurementForDevice(deviceType);
                if(deviceNeedsBalancing(deviceType, measurement)){
                    parametersToBeBalances.add(deviceType);
                }
                new Measurement(deviceType,measurement,States.BALANCED,currentTime).saveMeasurement();
            }
        }
        return parametersToBeBalances;
    }

    private boolean deviceNeedsBalancing(EnvironmentDeviceTypes deviceType,Double currentValue) {
        EnvironmentPropertyParameter parameterForDevice = inputParameterProcessor.getEnvironmentPropertyForDevice(deviceType);
        return parameterForDevice.getMin() > currentValue || parameterForDevice.getMax() < currentValue;
    }

    public boolean isGrowthTimeDue(long currentTime) {
        return inputParameterProcessor.getTotalGrowthTime().getValue() <= currentTime;
    }
}
