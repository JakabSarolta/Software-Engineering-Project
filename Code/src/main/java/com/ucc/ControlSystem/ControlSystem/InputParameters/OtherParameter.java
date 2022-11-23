package com.ucc.ControlSystem.ControlSystem.InputParameters;

import jakarta.persistence.*;

/**
 * Entity class that will be stored in the database. It holds values for special parameters like growth time.
 */

@Entity
@Table(name = "other_parameters")
public class OtherParameter implements InputParameter {

    @Id
    @Column(name = "type")
    private OtherParameters type;

    @Column(name = "value")
    private long value;

    @Transient
    private long validValueMax;

    @Transient
    private long validValueMin;

    public OtherParameter() {
    }

    public OtherParameter(OtherParameters type, long value, long valid_value_MAX, long getValid_value_MIN) {
        this.type = type;
        this.value = value;
        this.validValueMax = valid_value_MAX;
        this.validValueMin = getValid_value_MIN;
    }

    @Override
    public boolean isValid() {
        return value >= validValueMin && value <= validValueMax;
    }

    @Override
    public OtherParameter clone(){
        return new OtherParameter(type,value,validValueMax,validValueMin);
    }

    @Override
    public OtherParameters getType() {
        return type;
    }

    public void setType(OtherParameters type) {
        this.type = type;
    }

    public long getValidValueMax() {
        return validValueMax;
    }

    public long getValidValueMin() {
        return validValueMin;
    }

    public void setValidValueMax(long validValueMax) {
        this.validValueMax = validValueMax;
    }

    public void setValidValueMin(long validValueMin) {
        this.validValueMin = validValueMin;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
