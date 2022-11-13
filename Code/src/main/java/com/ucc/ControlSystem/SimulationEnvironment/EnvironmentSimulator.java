package com.ucc.ControlSystem.SimulationEnvironment;

import com.ucc.ControlSystem.Utils.TimeConvertor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class EnvironmentSimulator {

    private static EnvironmentSimulator environmentSimulator = null;

    private Map<EnvironmentDeviceTypes,Timestamp> lastMeasuredTimes;
    private Map<EnvironmentDeviceTypes,Double> lastMeasuredValues;

    private final ArrayList<EnvironmentDevice> sensors;
    private final ArrayList<EnvironmentDevice> actuators;

    // todo: Move them and make them global for the system
    // how long we simulate for (salad time) and that time how many real life time is
    // I want to simulate 30 salad days in 1 minute
    private int durationOfTheSimulationSaladTime;
    private int durationOfTheSimulationRealLifeTime; // 1 minute

    private final double STARTING_TEMPERATURE = 20.0;



    private EnvironmentSimulator(){
        lastMeasuredTimes = new HashMap<>();
        lastMeasuredValues = new HashMap<>();

        sensors = new ArrayList<>();
        actuators = new ArrayList<>();

        initializeEnvironmentDevicesList();
        initializeMeasurementAndTimeList();

        durationOfTheSimulationSaladTime = 0;
        durationOfTheSimulationRealLifeTime = 0;
    }

    public Map<EnvironmentDeviceTypes,Double> takeMeasurements(){
        for(EnvironmentDeviceTypes edt : EnvironmentDeviceTypes.values()){
            takeMeasurement(edt);
        }
        return lastMeasuredValues;
    }

    public double takeMeasurement(EnvironmentDeviceTypes deviceType){
        double measurement = -1;

        Sensor sensor = (Sensor) findDevice(sensors,deviceType); // get the sensor based on its type
        Actuator actuator = (Actuator) findDevice(actuators,deviceType); // get actuator based on its type
        if(sensor == null || actuator == null){
            return -1;
        }

        Double lastMeasuredValue = this.lastMeasuredValues.get(deviceType); // get the last measurement of the sensor
        Timestamp timeLastMeasured = this.lastMeasuredTimes.get(deviceType); // get the time it last measured

        // compute the elapsed time since the last measurement, and convert it into seconds
        long elapsedTimeInSeconds = TimeConvertor.elapsedTimeInSeconds(timeLastMeasured, Timestamp.valueOf(LocalDateTime.now()));

        // compute that elapsed time how much salad time would be
//        double elapsedSaladTime = TimeConvertor.convertRealLifeTimeToSaladTime(elapsedTimeInSeconds);

        measurement = lastMeasuredValue + elapsedTimeInSeconds * sensor.getSensorTendency() +
                    elapsedTimeInSeconds * actuator.getActuatorStrength();

        // save the measurement value and time
        this.lastMeasuredValues.put(deviceType,measurement);
        this.lastMeasuredTimes.put(deviceType,Timestamp.valueOf(LocalDateTime.now()));

        return measurement;
    }

    public void setSensorTendency(EnvironmentDeviceTypes sensorType, double tendency){
        Sensor sensor = (Sensor) findDevice(sensors,sensorType);
        if(sensor == null){
            return;
        }

        takeMeasurement(sensorType); // take the measurement with the tendency that was set until now

        sensor.setSensorTendency(tendency); // update the tendency
    }

    public void setActuatorStrength(EnvironmentDeviceTypes actuatorType, double strength){
        Actuator actuator = (Actuator) findDevice(actuators,actuatorType);
        if(actuator == null){
            return;
        }

        takeMeasurement(actuatorType); // take the measurement with the strength that was set until now

        actuator.setActuatorStrength(strength); // set the new strength
    }

    private EnvironmentDevice findDevice(ArrayList<EnvironmentDevice> devices, EnvironmentDeviceTypes device){
        Optional<EnvironmentDevice> dev = devices.stream()
                .filter(d -> d.getType() == device)
                .findFirst();
        return dev.isPresent() ? dev.get() : null;
    }

    // ensure this class is singleton
    public static EnvironmentSimulator getEnvironmentSimulator(){
        if(environmentSimulator == null){
            environmentSimulator = new EnvironmentSimulator();
        }
        return environmentSimulator;
    }

    private void initializeEnvironmentDevicesList() {
        for(EnvironmentDeviceTypes d : EnvironmentDeviceTypes.values()){
            sensors.add(new Sensor(d));
            actuators.add(new Actuator(d));
        }
    }

    private void initializeMeasurementAndTimeList(){
        for (EnvironmentDeviceTypes d : EnvironmentDeviceTypes.values()){
            lastMeasuredTimes.put(d,Timestamp.valueOf(LocalDateTime.now()));
            lastMeasuredValues.put(d,STARTING_TEMPERATURE);
        }
    }

    public int getDurationOfTheSimulationSaladTime() {
        return durationOfTheSimulationSaladTime;
    }

    public int getDurationOfTheSimulationRealLifeTime() {
        return durationOfTheSimulationRealLifeTime;
    }

    public void setDurationOfTheSimulationSaladTime(int durationOfTheSimulationSaladTime) {
        this.durationOfTheSimulationSaladTime = durationOfTheSimulationSaladTime;
    }

    public void setDurationOfTheSimulationRealLifeTime(int durationOfTheSimulationRealLifeTime) {
        this.durationOfTheSimulationRealLifeTime = durationOfTheSimulationRealLifeTime;
    }
}
