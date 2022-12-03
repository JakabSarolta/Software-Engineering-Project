package com.ucc.ControlSystem.EnvironmentSimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindDeviceTest {
    @Test
    public void findDeviceTest(){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        Sensor mySensor = null;
        for (EnvironmentDevice sensor : es.getSensors()) {
            if(sensor.getType() == EnvironmentDeviceTypes.PH_LEVEL){
                mySensor = (Sensor) sensor;
            }
        }
        assertSame(mySensor, es.findDevice(es.getSensors(), EnvironmentDeviceTypes.PH_LEVEL));
    }
}
