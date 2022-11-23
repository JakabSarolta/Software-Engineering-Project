package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;

import java.util.Map;

/**
 * Class for the data collection state. It just calls functions from the
 * environment simulator. Its only job is to collect data without processing it.
 */
public class DataCollector {

    private static DataCollector dataCollector = null;

    private DataCollector(){

    }

    public static DataCollector getDataCollector(){
        if(dataCollector == null){
            dataCollector = new DataCollector();
        }
        return dataCollector;
    }

    public Map<EnvironmentDeviceTypes, Double> takeMeasurements(){
        return EnvironmentSimulator.getEnvironmentSimulator().takeMeasurements();
    }

    public Double takeMeasurementForDevice(EnvironmentDeviceTypes type){
        return EnvironmentSimulator.getEnvironmentSimulator().takeMeasurement(type);
    }

    public Double getSensorValue(EnvironmentDeviceTypes device){
        return EnvironmentSimulator.getEnvironmentSimulator().getSensorValueForDevice(device);
    }

    public String getActuatorStrength(EnvironmentDeviceTypes device){
        return EnvironmentSimulator.getEnvironmentSimulator().getActuatorStateForDevice(device);
    }

    public double getActuatorSetStrength(EnvironmentDeviceTypes device){
        return EnvironmentSimulator.getEnvironmentSimulator().getActuatorSetStrength(device);
    }

    public void setActuatorSetStrength(EnvironmentDeviceTypes device, double value){
        EnvironmentSimulator.getEnvironmentSimulator().setActuatorSetStrength(device,value);
    }

    public void setActuatorCurrentStrength(EnvironmentDeviceTypes device, double value){
        EnvironmentSimulator.getEnvironmentSimulator().setActuatorCurrentStrength(device,value);
    }

}
