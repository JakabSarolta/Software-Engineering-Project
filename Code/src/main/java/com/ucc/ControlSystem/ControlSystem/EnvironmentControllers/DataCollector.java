package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;

import java.util.Map;

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

}
