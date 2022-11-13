package com.ucc.ControlSystem.SystemConfiguration;

public enum SystemConfigParameters {
    APP_NAME("app.name"),
    DATABASE_URL("db.url"),
    DATABASE_USER("db.user"),
    DATABASE_PASS("db.pass");

    private final String paramValue;

    public String getParamValue(){
        return paramValue;
    }

    SystemConfigParameters(String paramValue){
        this.paramValue = paramValue;
    }
}
