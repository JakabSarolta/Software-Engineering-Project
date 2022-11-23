package com.ucc.ControlSystem.EnvironmentSimulator;

import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;
import com.ucc.ControlSystem.Utils.TimeConvertor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class containing the current state of the simulation. It holds data of the
 * measurement values, the time they were measured, the list of actuators and sensors
 * (and their state), growth time, and the simulation duration. It is also
 * responsible to compute the value of the next measurement.
 */
public class EnvironmentSimulator {

    private static EnvironmentSimulator environmentSimulator = null;

    private Map<EnvironmentDeviceTypes,Timestamp> lastMeasuredTimes;
    private Map<EnvironmentDeviceTypes,Double> lastMeasuredValues;

    private final ArrayList<EnvironmentDevice> sensors;
    private final ArrayList<EnvironmentDevice> actuators;

    private int durationOfTheSimulationSaladTime;
    private int durationOfTheSimulationRealLifeTime;

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

    /**
     * Takes measurement for all the sensors.
     * @return a map of all the sensors and their measured values
     */
    public Map<EnvironmentDeviceTypes,Double> takeMeasurements(){
        for(EnvironmentDeviceTypes edt : EnvironmentDeviceTypes.values()){
            takeMeasurement(edt);
        }
        return lastMeasuredValues;
    }

    /**
     * Takes measurement for only one sensor. It computes the current measurement value
     * by taking in consideration the last measured value and the time it was measured,
     * the amount the parameter changes in one seconds, and the strength of the actuator.
     * @param deviceType the device for which it computes the next value
     * @return the value computed for the device
     */
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
        double elapsedTimeInSeconds = TimeConvertor.elapsedTimeInMillis(timeLastMeasured, Timestamp.valueOf(LocalDateTime.now())) / 1000.0;

        measurement = lastMeasuredValue + elapsedTimeInSeconds * sensor.getSensorTendency() +
                    elapsedTimeInSeconds * actuator.getActuatorStrength();

        // save the measurement value and time
        this.lastMeasuredValues.put(deviceType,measurement);
        this.lastMeasuredTimes.put(deviceType,Timestamp.valueOf(LocalDateTime.now()));

        return measurement;
    }

    /**
     * Sets the amount with which the value of the parameter changes in one second (real life)
     * @param sensorType the device type
     * @param tendency the slope of the values of the parameter
     */
    public void setSensorTendency(EnvironmentDeviceTypes sensorType, double tendency){
        Sensor sensor = (Sensor) findDevice(sensors,sensorType);
        if(sensor == null){
            return;
        }

        takeMeasurement(sensorType); // take the measurement with the tendency that was set until now

        sensor.setSensorTendency(tendency); // update the tendency
    }

    /**
     * Sets the amount with which the actuator tries to counter-act the growth of the parameter
     * @param actuatorType the device type
     * @param strength the strength of the actuator
     */
    public void setActuatorStrength(EnvironmentDeviceTypes actuatorType, double strength){
        Actuator actuator = (Actuator) findDevice(actuators,actuatorType);
        if(actuator == null){
            return;
        }

        takeMeasurement(actuatorType); // take the measurement with the strength that was set until now

        actuator.setActuatorStrength(strength); // set the new strength
    }

    /**
     * Finds an entity of a device from the list of devices based on its type
     * @param devices the list of devices from which it selects it
     * @param device the device type we are looking for
     * @return the device
     */
    private EnvironmentDevice findDevice(ArrayList<EnvironmentDevice> devices, EnvironmentDeviceTypes device){
        Optional<EnvironmentDevice> dev = devices.stream()
                .filter(d -> d.getType() == device)
                .findFirst();
        return dev.isPresent() ? dev.get() : null;
    }

    /**
     * ensure this class is singleton
    */
    public static EnvironmentSimulator getEnvironmentSimulator(){
        if(environmentSimulator == null){
            environmentSimulator = new EnvironmentSimulator();
        }
        return environmentSimulator;
    }

    /**
     * Initializes the lists of environment devices (actuators and sensors)
     */
    private void initializeEnvironmentDevicesList() {
        for(EnvironmentDeviceTypes d : EnvironmentDeviceTypes.values()){
            sensors.add(new Sensor(d));
            actuators.add(new Actuator(d));
        }
    }

    /**
     * Initializes the list of last measured values by the default values written in
     * the system configuration file (app.conf)
     */
    private void initializeMeasurementAndTimeList(){
        for (EnvironmentDeviceTypes d : EnvironmentDeviceTypes.values()){
            lastMeasuredTimes.put(d,Timestamp.valueOf(LocalDateTime.now()));
            lastMeasuredValues.put(d,
                    Double.parseDouble(
                    SystemConfigurationReader.getSystemConfigurationReader().readParam(SystemConfigParameters.valueOf(d + "_DEFAULT_VALUE"))));
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

    public double getSensorValueForDevice(EnvironmentDeviceTypes device){
        return lastMeasuredValues.get(device);
    }

    public String getActuatorStateForDevice(EnvironmentDeviceTypes device){
        return ((Actuator) findDevice(actuators,device)).getActuatorStrength() == 0 ? "OFF" : "ON";
    }

    public Map<EnvironmentDeviceTypes, Double> getLastMeasuredValues() {
        return lastMeasuredValues;
    }
}
