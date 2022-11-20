package com.ucc.ControlSystem.SystemConfiguration;

public enum SystemConfigParameters {
    APP_NAME("app.name"),
    DATABASE_URL("db.url"),
    DATABASE_USER("db.user"),
    DATABASE_PASS("db.pass"),
    LIGHT_ON_TIME_MIN("light.time.min"),
    LIGHT_ON_TIME_MAX("light.time.max"),
    LIGHT_OFF_TIME_MIN("light.time.min"),
    LIGHT_OFF_TIME_MAX("light.time.max"),
    GROWTH_TIME_MIN("growth.time.min"),
    GROWTH_TIME_MAX("growth.time.max"),
    MEASUREMENT_INTERVAL_MIN("measurement.interval.min"),
    MEASUREMENT_INTERVAL_MAX("measurement.interval.max"),
    AIR_TEMPERATURE_VALID_MIN("air.temp.valid.min"),
    AIR_TEMPERATURE_VALID_MAX("air.temp.valid.max"),
    AIR_TEMPERATURE_PRECISION("air.temp.precision"),
    BALANCED_CHECK_INTERVAL("balanced.check.interval");

    private final String paramValue;

    public String getParamValue(){
        return paramValue;
    }

    SystemConfigParameters(String paramValue){
        this.paramValue = paramValue;
    }
}
