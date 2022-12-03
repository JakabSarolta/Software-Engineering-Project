package com.ucc.ControlSystem.Utils;

import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;

import java.util.Map;

public interface DisplayAdapterInterface {
    void displayAlert(String message, String type, int severity);
    void displayCurrentTime(long currentTimeInSeconds);
    void displayCurrentValues(Map<EnvironmentDeviceTypes,Double> currentSensorValues);
}
