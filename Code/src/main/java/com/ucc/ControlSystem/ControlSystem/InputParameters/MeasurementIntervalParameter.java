package com.ucc.ControlSystem.ControlSystem.InputParameters;

import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;
import jakarta.persistence.*;

@Entity
@Table(name = "measurement_interval_parameters")
public class MeasurementIntervalParameter implements InputParameter{
    public static final int MEASUREMENT_INTERVAL_MIN = getMeasurementInterval(SystemConfigParameters.MEASUREMENT_INTERVAL_MIN);
    public static final int MEASUREMENT_INTERVAL_MAX = getMeasurementInterval(SystemConfigParameters.MEASUREMENT_INTERVAL_MAX);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "interval_balanced_state")
    private int intervalBalancedState;

    @Column(name = "interval_balancing_state")
    private int intervalBalancingState;

    @Column(name = "property_type", unique = true)
    private EnvironmentDeviceTypes propertyType;

    public MeasurementIntervalParameter() {
    }

    public MeasurementIntervalParameter(int intervalBalancedState, int intervalBalancingState, EnvironmentDeviceTypes propertyType) {
        this.intervalBalancedState = intervalBalancedState;
        this.intervalBalancingState = intervalBalancingState;
        this.propertyType = propertyType;
    }

    public MeasurementIntervalParameter(EnvironmentDeviceTypes propertyType) {
        this.propertyType = propertyType;
    }

    public boolean isValid(){
        return (intervalBalancedState >= MEASUREMENT_INTERVAL_MIN && intervalBalancedState <= MEASUREMENT_INTERVAL_MAX) &&
               (intervalBalancingState >= MEASUREMENT_INTERVAL_MIN && intervalBalancingState <= MEASUREMENT_INTERVAL_MAX);
    }

    private static int getMeasurementInterval(SystemConfigParameters param){
        return Integer.parseInt(SystemConfigurationReader.getSystemConfigurationReader().readParam(param));
    }

    public int getIntervalBalancedState() {
        return intervalBalancedState;
    }

    public void setIntervalBalancedState(int intervalBalancedState) {
        this.intervalBalancedState = intervalBalancedState;
    }

    public int getIntervalBalancingState() {
        return intervalBalancingState;
    }

    public void setIntervalBalancingState(int intervalBalancingState) {
        this.intervalBalancingState = intervalBalancingState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EnvironmentDeviceTypes getPropertyType() {
        return propertyType;
    }
}
