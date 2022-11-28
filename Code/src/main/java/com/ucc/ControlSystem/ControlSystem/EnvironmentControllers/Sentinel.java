package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.InputParameters.EnvironmentPropertyParameter;
import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class corresponding to the balanced state.
 */
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

    /**
     * Called periodically to check whether parameters need to be balanced. It does the check at intervals
     * set in the intervalBalanced field.
     * @param currentTime the current time of the simulation in seconds (salad lifetime)
     * @param parametersToBeBalanced the list to which it adds the devices that need balancing
     */
    public void checkPeriodically(long currentTime, List<EnvironmentDeviceTypes> parametersToBeBalanced){
        for(EnvironmentDeviceTypes deviceType : EnvironmentDeviceTypes.values()){
            long checkIntervalForDevice = inputParameterProcessor.
                    getBalancedCheckIntervalForDevice(deviceType);

            if(currentTime % checkIntervalForDevice == 0){
                Double measurement = dataCollector.takeMeasurementForDevice(deviceType);
                if(deviceNeedsBalancing(deviceType, dataCollector.takeMeasurementForDevice(deviceType))){
                    parametersToBeBalanced.add(deviceType);
                }
                new Measurement(deviceType,measurement,States.BALANCED,currentTime).saveMeasurement();
            }
        }
    }

    /**
     * Checks if the current value measured by the sensor is between the normal ranges given
     * @param deviceType the device we are checking
     * @param currentValue the current measured value
     * @return true if the device needs balancing
     */
    private boolean deviceNeedsBalancing(EnvironmentDeviceTypes deviceType, Double currentValue) {
        EnvironmentPropertyParameter parameterForDevice =
                inputParameterProcessor.getEnvironmentPropertyForDevice(deviceType);

        return parameterForDevice.getMin() > currentValue || parameterForDevice.getMax() < currentValue;
    }

    /**
     * Checks if the salad should be harvested
     * @param currentTime the current time in seconds (salad lifetime)
     * @return true if the control system should be stopped
     */
    public boolean isGrowthTimeDue(long currentTime) {
        return inputParameterProcessor.getTotalGrowthTime().getValue() <= currentTime;
    }
}
