package com.ucc.ControlSystem.EnvironmentSimulator;

public class Sensor implements EnvironmentDevice{

    private double sensorTendency;
    private final EnvironmentDeviceTypes type;

    public Sensor(EnvironmentDeviceTypes sensorsType) {
        this.type = sensorsType;
        this.sensorTendency = 0;
    }

    public EnvironmentDeviceTypes getType() {
        return type;
    }

    public double getSensorTendency() {
        return sensorTendency;
    }

    public void setSensorTendency(double sensorTendency) {
        this.sensorTendency = sensorTendency;
    }
}
