package com.ucc.ControlSystem.EnvironmentSimulator;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TakeMeasurementTest {
    @Test
    public void takeMeasurementTest(){
        EnvironmentDeviceTypes edt = EnvironmentDeviceTypes.AIR_TEMPERATURE;

        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();

        es.setSensorTendency(edt,0.2);
        es.setActuatorCurrentStrength(edt,-0.1);
        es.getLastMeasuredValues().put(edt,17.5);
        es.getLastMeasuredTimes().put(edt,Timestamp.valueOf(LocalDateTime.now().minusSeconds(10)));

        double measurement = es.takeMeasurement(edt);


        assertEquals(18.5, Math.round(measurement * 10.0) / 10.0);
    }
}
