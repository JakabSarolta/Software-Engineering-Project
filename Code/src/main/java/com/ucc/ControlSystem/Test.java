package com.ucc.ControlSystem;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Test {
    @Id
    private int ID;

    private boolean field1;
    private double field2;

    public Test(int ID, boolean field1, double field2) {
        this.ID = ID;
        this.field1 = field1;
        this.field2 = field2;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isField1() {
        return field1;
    }

    public void setField1(boolean field1) {
        this.field1 = field1;
    }

    public double getField2() {
        return field2;
    }

    public void setField2(double field2) {
        this.field2 = field2;
    }
}
