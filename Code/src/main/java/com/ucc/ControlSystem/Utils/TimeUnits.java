package com.ucc.ControlSystem.Utils;

public enum TimeUnits {
    SECOND(1),
    MINUTE(60),
    HOUR(3600),
    DAY(86400);

    private int val;

    public int getVal() {
        return val;
    }

    TimeUnits(int val){
        this.val = val;
    }
}
