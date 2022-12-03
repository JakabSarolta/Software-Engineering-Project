package com.ucc.ControlSystem.EnvironmentSimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetActuatorCurrentStrengthTest {
    @Test
    public void setActuatorCurrentStrength(){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setActuatorCurrentStrength(EnvironmentDeviceTypes.WATER_LEVEL,-0.2);
        assertEquals(-0.2, es.getActuatorByType(EnvironmentDeviceTypes.WATER_LEVEL).getCurrentStrength());
    }
}
