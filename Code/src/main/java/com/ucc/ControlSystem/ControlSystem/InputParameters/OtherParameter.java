package com.ucc.ControlSystem.ControlSystem.InputParameters;

import jakarta.persistence.*;

@Entity
@Table(name = "other_parameters")
public class OtherParameter implements InputParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private OtherParameters type;

    @Column(name = "value", unique = true)
    private long value;

    @Transient
    private long valid_value_MAX;

    @Transient
    private long valid_value_MIN;

    public OtherParameter() {
    }

    public OtherParameter(OtherParameters type, long value, long valid_value_MAX, long getValid_value_MIN) {
        this.type = type;
        this.value = value;
        this.valid_value_MAX = valid_value_MAX;
        this.valid_value_MIN = getValid_value_MIN;
    }

    @Override
    public boolean isValid() {
        return value >= valid_value_MIN && value <= valid_value_MAX;
    }

    public OtherParameters getType() {
        return type;
    }

    public void setType(OtherParameters type) {
        this.type = type;
    }

    public long getValid_value_MAX() {
        return valid_value_MAX;
    }

    public long getValid_value_MIN() {
        return valid_value_MIN;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
