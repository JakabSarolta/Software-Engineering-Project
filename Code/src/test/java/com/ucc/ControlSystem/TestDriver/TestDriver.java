package com.ucc.ControlSystem.TestDriver;

import com.ucc.ControlSystem.ControlSystem.InputParameters.*;
import com.ucc.ControlSystem.EnvironmentSimulator.Controller;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;
import com.ucc.ControlSystem.Utils.ConsoleDisplayAdapterInterfaceIml;

public class TestDriver {

    private final int SIMULATION_DURATION_REAL_LIFE = 120;
    private final int SIMULATION_DURATION_SALAD_TIME = 5 * 60 * 60; // 5 hours

    private final int GROWTH_TIME = 30;

    private final double AIR_TEMPERATURE_MIN = 16;
    private final double AIR_TEMPERATURE_MAX = 18;
    private final int AIR_TEMPERATURE_BALANCED = 35;
    private final int AIR_TEMPERATURE_BALANCING = 35;

    private final double WATER_TEMPERATURE_MIN = 8.5;
    private final double WATER_TEMPERATURE_MAX = 18.5;
    private final int WATER_TEMPERATURE_BALANCED = 40;
    private final int WATER_TEMPERATURE_BALANCING = 10;

    private final double HUMIDITY_MIN = 50.0;
    private final double HUMIDITY_MAX = 85.0;
    private final int HUMIDITY_BALANCED = 30;
    private final int HUMIDITY_BALANCING = 3;

    private final double PH_LEVEL_MIN = 6.0;
    private final double PH_LEVEL_MAX = 6.6;
    private final int PH_LEVEL_BALANCED = 90;
    private final int PH_LEVEL_BALANCING = 40;

    private final double EC_MIN = 0.6;
    private final double EC_MAX = 8.2;
    private final int EC_BALANCED = 95;
    private final int EC_BALANCING = 2;

    private final double WATER_LEVEL_MIN = 60.0;
    private final double WATER_LEVEL_MAX = 85.0;
    private final int WATER_LEVEL_BALANCED = 60;
    private final int WATER_LEVEL_BALANCING = 1;

    private static TestDriver testDriver = null;

    private TestDriver(){
        initializeInputParameterProcessor();
        initializeEnvironmentSimulator();
    }

    private void initializeEnvironmentSimulator() {
    }

    private void initializeInputParameterProcessor() {
        InputParameterProcessor processor = InputParameterProcessor.getInputParameterProcessor();

        processor.updateEnvironmentPropertyParameter(AIR_TEMPERATURE_MIN, AIR_TEMPERATURE_MAX, EnvironmentDeviceTypes.AIR_TEMPERATURE);
        processor.updateEnvironmentPropertyParameter(WATER_TEMPERATURE_MIN, WATER_TEMPERATURE_MAX, EnvironmentDeviceTypes.WATER_TEMPERATURE);
        processor.updateEnvironmentPropertyParameter(HUMIDITY_MIN, HUMIDITY_MAX, EnvironmentDeviceTypes.HUMIDITY);
        processor.updateEnvironmentPropertyParameter(PH_LEVEL_MIN, PH_LEVEL_MAX, EnvironmentDeviceTypes.PH_LEVEL);
        processor.updateEnvironmentPropertyParameter(EC_MIN, EC_MAX, EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY);
        processor.updateEnvironmentPropertyParameter(WATER_LEVEL_MIN, WATER_LEVEL_MAX, EnvironmentDeviceTypes.WATER_LEVEL);

        processor.updateMeasurementIntervalParameter(AIR_TEMPERATURE_BALANCED,AIR_TEMPERATURE_BALANCING,EnvironmentDeviceTypes.AIR_TEMPERATURE);
        processor.updateEnvironmentPropertyParameter(WATER_TEMPERATURE_BALANCED,WATER_TEMPERATURE_BALANCING,EnvironmentDeviceTypes.WATER_TEMPERATURE);
        processor.updateEnvironmentPropertyParameter(HUMIDITY_BALANCED,HUMIDITY_BALANCING,EnvironmentDeviceTypes.HUMIDITY);
        processor.updateEnvironmentPropertyParameter(PH_LEVEL_BALANCED,PH_LEVEL_BALANCING,EnvironmentDeviceTypes.PH_LEVEL);
        processor.updateEnvironmentPropertyParameter(EC_BALANCED,EC_BALANCING,EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY);
        processor.updateEnvironmentPropertyParameter(WATER_LEVEL_BALANCED,WATER_LEVEL_BALANCING,EnvironmentDeviceTypes.WATER_LEVEL);

        processor.updateOtherParameter(OtherParameters.GROWTH_TIME, GROWTH_TIME);
    }

    public void setDeviceSensorRange(EnvironmentDeviceTypes device, double min, double max){
        InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(min, max, device);
    }

    public void setDeviceMeasurementIntervalRange(EnvironmentDeviceTypes device, int balanced, int balancing){
        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(balanced,balancing,device);
    }

    public void setActuatorSetStrength(EnvironmentDeviceTypes device, double strength){
        EnvironmentSimulator.getEnvironmentSimulator().setActuatorSetStrength(device,strength);
    }

    public double getSensorValue(EnvironmentDeviceTypes device){
        return EnvironmentSimulator.getEnvironmentSimulator().getSensorValueForDevice(device);
    }

    public void startSimulation(){
        Controller.startSimulation(new ConsoleDisplayAdapterInterfaceIml(),SIMULATION_DURATION_SALAD_TIME,SIMULATION_DURATION_REAL_LIFE);
    }

    public static TestDriver getInstance(){
        if(testDriver == null){
            testDriver = new TestDriver();
        }
        return testDriver;
    }
}
