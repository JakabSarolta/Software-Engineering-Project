package com.ucc.ControlSystem.ControlSystem.InputParameters;

import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import jakarta.persistence.*;

@Entity
@Table(name = "environment_property_parameter")
public class EnvironmentPropertyParameter implements InputParameter{

    @Column(name = "min")
    private double min;

    @Column(name = "max")
    private double max;

    @Transient
    private double validMin;

    @Transient
    private double validMax;

    @Transient
    private double precision;

    @Id
    @Column(name =  "type")
    private EnvironmentDeviceTypes propertyType;

    public EnvironmentPropertyParameter() {
    }

    public EnvironmentPropertyParameter(double min, double max, double valid_min, double valid_max, double max_precision, EnvironmentDeviceTypes propertyType) {
        this.min = min;
        this.max = max;
        this.validMin = valid_min;
        this.validMax = valid_max;
        this.precision = max_precision;
        this.propertyType = propertyType;
    }

    public EnvironmentPropertyParameter(double valid_min, double valid_max, double max_precision, EnvironmentDeviceTypes propertyType) {
        this.validMin = valid_min;
        this.validMax = valid_max;
        this.precision = max_precision;
        this.propertyType = propertyType;
    }

    public boolean isValid(){
        return this.min >= this.validMin && this.max <= this.validMax;
    }

    @Override
    public EnvironmentPropertyParameter clone(){
        return new EnvironmentPropertyParameter(min,max,validMin,validMax,precision,propertyType);
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

    @Override
    public EnvironmentDeviceTypes getType() {
        return propertyType;
    }

    public double getValidMin() {
        return validMin;
    }

    public double getValidMax() {
        return validMax;
    }

    public void setValidMin(double validMin) {
        this.validMin = validMin;
    }

    public void setValidMax(double validMax) {
        this.validMax = validMax;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getPrecision() {
        return precision;
    }
}
