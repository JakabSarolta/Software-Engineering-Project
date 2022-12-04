package com.ucc.ControlSystem.TestDriver;

import com.ucc.ControlSystem.ControlSystem.InputParameters.*;
import com.ucc.ControlSystem.EnvironmentSimulator.Controller;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;
import com.ucc.ControlSystem.Utils.ConsoleDisplayAdapterInterfaceIml;

public class TestDriver {

    private final int SIMULATION_DURATION_REAL_LIFE = 120;
    private final int SIMULATION_DURATION_SALAD_TIME = 5 * 60 * 60; // 5 hours

    private final int GROWTH_TIME = 30 * 86400;

    private final double AIR_TEMPERATURE_MIN = 16;
    private final double AIR_TEMPERATURE_MAX = 18;
    private final int AIR_TEMPERATURE_BALANCED = 35;
    private final int AIR_TEMPERATURE_BALANCING = 35;
    private final double AIR_TEMPERATURE_SIGMA = 0.1;
    private final double AIR_TEMPERATURE_ALPHA = 0.2;

    private final double WATER_TEMPERATURE_MIN = 8.5;
    private final double WATER_TEMPERATURE_MAX = 18.5;
    private final int WATER_TEMPERATURE_BALANCED = 40;
    private final int WATER_TEMPERATURE_BALANCING = 10;
    private final double WATER_TEMPERATURE_SIGMA = -0.1;
    private final double WATER_TEMPERATURE_ALPHA = 0.2;

    private final double HUMIDITY_MIN = 49.0;
    private final double HUMIDITY_MAX = 85.0;
    private final int HUMIDITY_BALANCED = 30;
    private final int HUMIDITY_BALANCING = 3;
    private final double HUMIDITY_SIGMA = -0.1;
    private final double HUMIDITY_ALPHA = 0.2;

    private final double PH_LEVEL_MIN = 6.0;
    private final double PH_LEVEL_MAX = 6.6;
    private final int PH_LEVEL_BALANCED = 90;
    private final int PH_LEVEL_BALANCING = 40;
    private final double PH_SIGMA = 0.01;
    private final double PH_ALPHA = -0.02;

    private final double EC_MIN = 0.6;
    private final double EC_MAX = 8.2;
    private final int EC_BALANCED = 95;
    private final int EC_BALANCING = 2;
    private final double EC_SIGMA = 0.01;
    private final double EC_ALPHA = -0.02;

    private final double WATER_LEVEL_MIN = 60.0;
    private final double WATER_LEVEL_MAX = 85.0;
    private final int WATER_LEVEL_BALANCED = 60;
    private final int WATER_LEVEL_BALANCING = 1;
    private final double WATER_LEVEL_SIGMA = -0.5;
    private final double WATER_LEVEL_ALPHA = 1;

    private static TestDriver testDriver = null;

    private TestDriver(){
        initializeInputParameterProcessor();
        initializeEnvironmentSimulator();
    }

    private void initializeEnvironmentSimulator() {
        setDeviceMeasurementIntervalRange(EnvironmentDeviceTypes.AIR_TEMPERATURE, AIR_TEMPERATURE_BALANCED, AIR_TEMPERATURE_BALANCING);
        setDeviceMeasurementIntervalRange(EnvironmentDeviceTypes.WATER_TEMPERATURE, WATER_TEMPERATURE_BALANCED, WATER_TEMPERATURE_BALANCING);
        setDeviceMeasurementIntervalRange(EnvironmentDeviceTypes.HUMIDITY, HUMIDITY_BALANCED, HUMIDITY_BALANCING);
        setDeviceMeasurementIntervalRange(EnvironmentDeviceTypes.PH_LEVEL, PH_LEVEL_BALANCED, PH_LEVEL_BALANCING);
        setDeviceMeasurementIntervalRange(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY, EC_BALANCED, EC_BALANCING);
        setDeviceMeasurementIntervalRange(EnvironmentDeviceTypes.WATER_LEVEL, WATER_LEVEL_BALANCED, WATER_LEVEL_BALANCING);

        setSensorSetStrength(EnvironmentDeviceTypes.AIR_TEMPERATURE, AIR_TEMPERATURE_SIGMA);
        setSensorSetStrength(EnvironmentDeviceTypes.WATER_TEMPERATURE, WATER_TEMPERATURE_SIGMA);
        setSensorSetStrength(EnvironmentDeviceTypes.HUMIDITY, HUMIDITY_SIGMA);
        setSensorSetStrength(EnvironmentDeviceTypes.PH_LEVEL, PH_SIGMA);
        setSensorSetStrength(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY, EC_SIGMA);
        setSensorSetStrength(EnvironmentDeviceTypes.WATER_LEVEL, WATER_LEVEL_SIGMA);

        setActuatorSetStrength(EnvironmentDeviceTypes.AIR_TEMPERATURE, AIR_TEMPERATURE_ALPHA);
        setActuatorSetStrength(EnvironmentDeviceTypes.WATER_TEMPERATURE, WATER_TEMPERATURE_ALPHA);
        setActuatorSetStrength(EnvironmentDeviceTypes.HUMIDITY, HUMIDITY_ALPHA);
        setActuatorSetStrength(EnvironmentDeviceTypes.PH_LEVEL, PH_ALPHA);
        setActuatorSetStrength(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY, EC_ALPHA);
        setActuatorSetStrength(EnvironmentDeviceTypes.WATER_LEVEL, WATER_LEVEL_ALPHA);
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
        processor.updateMeasurementIntervalParameter(WATER_TEMPERATURE_BALANCED,WATER_TEMPERATURE_BALANCING,EnvironmentDeviceTypes.WATER_TEMPERATURE);
        processor.updateMeasurementIntervalParameter(HUMIDITY_BALANCED,HUMIDITY_BALANCING,EnvironmentDeviceTypes.HUMIDITY);
        processor.updateMeasurementIntervalParameter(PH_LEVEL_BALANCED,PH_LEVEL_BALANCING,EnvironmentDeviceTypes.PH_LEVEL);
        processor.updateMeasurementIntervalParameter(EC_BALANCED,EC_BALANCING,EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY);
        processor.updateMeasurementIntervalParameter(WATER_LEVEL_BALANCED,WATER_LEVEL_BALANCING,EnvironmentDeviceTypes.WATER_LEVEL);

        processor.updateOtherParameter(OtherParameters.GROWTH_TIME, GROWTH_TIME);
    }

    public void updateDeviceSensorRange(EnvironmentDeviceTypes device, double min, double max){
        InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(min, max, device);
    }

    public void setDeviceMeasurementIntervalRange(EnvironmentDeviceTypes device, int balanced, int balancing){
        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(balanced * 60,balancing * 60,device);
    }

    public void setSensorSetStrength(EnvironmentDeviceTypes device, double strength){
        EnvironmentSimulator.getEnvironmentSimulator().setSensorTendency(device,strength);
    }

    public void setActuatorSetStrength(EnvironmentDeviceTypes device, double strength){
        EnvironmentSimulator.getEnvironmentSimulator().setActuatorSetStrength(device,strength);
    }

    public double getSensorValue(EnvironmentDeviceTypes device){
        return EnvironmentSimulator.getEnvironmentSimulator().takeMeasurement(device);
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
