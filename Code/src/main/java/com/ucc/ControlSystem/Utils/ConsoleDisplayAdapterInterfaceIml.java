package com.ucc.ControlSystem.Utils;

import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import java.util.Map;

public class ConsoleDisplayAdapterInterfaceIml implements DisplayAdapterInterface{
    @Override
    public void displayAlert(String message, String type, int severity) {
        String severityString = "";
        switch (severity){
            case 0 : severityString = "ERROR";
                break;
            case 1 : severityString = "INFO";
                break;
        }
        System.out.println("[ALERT]["+severityString+"] Reason: " + message);
    }

    @Override
    public void displayCurrentTime(long currentTimeInSeconds) {
        System.out.println("[TIME] Current time: " + TimeConvertor.convertSeconds(currentTimeInSeconds));
    }

    @Override
    public void displayCurrentValues(Map<EnvironmentDeviceTypes, Double> currentSensorValues) {
        System.out.println("[MEASUREMENT] Measured values:");
        for(EnvironmentDeviceTypes device: currentSensorValues.keySet()){
            System.out.println("  ["+device+"] " + currentSensorValues.get(device));
        }
    }
}
