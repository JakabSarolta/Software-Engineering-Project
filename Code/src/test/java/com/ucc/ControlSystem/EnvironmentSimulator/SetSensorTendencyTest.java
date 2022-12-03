package com.ucc.ControlSystem.EnvironmentSimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetSensorTendencyTest {
    @Test
    public void setSensorTendency(){
        EnvironmentDeviceTypes waterTemp = EnvironmentDeviceTypes.WATER_TEMPERATURE;
        double tendency = 0.3;
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setSensorTendency(waterTemp,tendency);
        assertEquals(0.3, es.getSensorByType(waterTemp).getSensorTendency());
    }
}
