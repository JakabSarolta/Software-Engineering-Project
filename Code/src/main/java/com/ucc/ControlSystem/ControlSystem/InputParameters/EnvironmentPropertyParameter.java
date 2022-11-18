package com.ucc.ControlSystem.ControlSystem.InputParameters;

import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import jakarta.persistence.*;

@Entity
@Table(name = "environment_property_parameter")
public class EnvironmentPropertyParameter implements InputParameter{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "min")
    private double min;

    @Column(name = "max")
    private double max;

    @Transient
    private double valid_min;

    @Transient
    private double valid_max;

    @Transient
    private double precision;

    @Column(name =  "property_type", unique = true)
    private EnvironmentDeviceTypes propertyType;

    public EnvironmentPropertyParameter() {
    }

    public EnvironmentPropertyParameter(double min, double max, double valid_min, double valid_max, double max_precision, EnvironmentDeviceTypes propertyType) {
        this.min = min;
        this.max = max;
        this.valid_min = valid_min;
        this.valid_max = valid_max;
        this.precision = max_precision;
        this.propertyType = propertyType;
    }

    public EnvironmentPropertyParameter(double valid_min, double valid_max, double max_precision, EnvironmentDeviceTypes propertyType) {
        this.valid_min = valid_min;
        this.valid_max = valid_max;
        this.precision = max_precision;
        this.propertyType = propertyType;
    }

    public boolean isValid(){
        return this.min >= this.valid_min && this.max <= this.valid_max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public EnvironmentDeviceTypes getPropertyType() {
        return propertyType;
    }

    public double getValid_min() {
        return valid_min;
    }

    public double getValid_max() {
        return valid_max;
    }

    public double getPrecision() {
        return precision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
