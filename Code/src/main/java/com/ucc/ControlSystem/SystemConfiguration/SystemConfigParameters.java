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

    AIR_TEMPERATURE_DEFAULT_VALUE("air.temperature.default"),
    AIR_TEMPERATURE_VALID_MIN("air.temp.valid.min"),
    AIR_TEMPERATURE_VALID_MAX("air.temp.valid.max"),
    AIR_TEMPERATURE_PRECISION("air.temp.precision"),

    WATER_TEMPERATURE_DEFAULT_VALUE("water.temperature.default"),
    WATER_TEMPERATURE_VALID_MIN("water.temp.valid.min"),
    WATER_TEMPERATURE_VALID_MAX("water.temp.valid.max"),
    WATER_TEMPERATURE_PRECISION("water.temp.precision"),

    HUMIDITY_DEFAULT_VALUE("humidity.default"),
    HUMIDITY_VALID_MIN("humidity.valid.min"),
    HUMIDITY_VALID_MAX("humidity.valid.max"),
    HUMIDITY_PRECISION("humidity.precision"),

    PH_LEVEL_DEFAULT_VALUE("ph.level.default"),
    PH_LEVEL_VALID_MIN("ph.level.valid.min"),
    PH_LEVEL_VALID_MAX("ph.level.valid.max"),
    PH_LEVEL_PRECISION("ph.level.precision"),

    ELECTRICAL_CONDUCTIVITY_DEFAULT_VALUE("ec.cond.default"),
    ELECTRICAL_CONDUCTIVITY_VALID_MIN("ec.cond.valid.min"),
    ELECTRICAL_CONDUCTIVITY_VALID_MAX("ec.cond.valid.max"),
    ELECTRICAL_CONDUCTIVITY_PRECISION("ec.cond.precision"),

    BALANCED_CHECK_INTERVAL("balanced.check.interval");

    private final String paramValue;

    public String getParamValue(){
        return paramValue;
    }

    SystemConfigParameters(String paramValue){
        this.paramValue = paramValue;
    }
}
