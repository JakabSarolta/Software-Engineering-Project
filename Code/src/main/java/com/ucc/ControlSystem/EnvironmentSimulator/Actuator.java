package com.ucc.ControlSystem.EnvironmentSimulator;

public class Actuator implements EnvironmentDevice{

    private double actuatorStrength;
    private final EnvironmentDeviceTypes type;

    public Actuator(EnvironmentDeviceTypes type) {
        this.type = type;
        this.actuatorStrength = 0;
    }

    public EnvironmentDeviceTypes getType() {
        return type;
    }

    public double getActuatorStrength() {
        return actuatorStrength;
    }

    public void setActuatorStrength(double actuatorStrength) {
        this.actuatorStrength = actuatorStrength;
    }
}

