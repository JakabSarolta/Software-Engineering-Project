package com.ucc.ControlSystem.EnvironmentSimulator;

// POJO (plain old java object) class for actuators
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

