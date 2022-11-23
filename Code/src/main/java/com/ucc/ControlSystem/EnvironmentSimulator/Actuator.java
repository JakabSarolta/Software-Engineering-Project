package com.ucc.ControlSystem.EnvironmentSimulator;

// POJO (plain old java object) class for actuators
public class Actuator implements EnvironmentDevice{

    private double currentStrength;
    private double setStrength;
    private final EnvironmentDeviceTypes type;

    public Actuator(EnvironmentDeviceTypes type) {
        this.type = type;
        this.currentStrength = 0;
    }

    public EnvironmentDeviceTypes getType() {
        return type;
    }

    public double getCurrentStrength() {
        return currentStrength;
    }

    public void setCurrentStrength(double currentStrength) {
        this.currentStrength = currentStrength;
    }

    public double getSetStrength() {
        return setStrength;
    }

    public void setSetStrength(double setStrength) {
        this.setStrength = setStrength;
    }
}

