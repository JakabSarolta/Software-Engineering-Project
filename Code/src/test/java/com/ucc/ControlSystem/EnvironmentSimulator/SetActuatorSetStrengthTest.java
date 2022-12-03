package com.ucc.ControlSystem.EnvironmentSimulator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetActuatorSetStrengthTest {
    @Test
    public void setActuatorSetStrengthTest(){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setActuatorSetStrength(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY,0.1);
        assertEquals(0.1, es.getActuatorByType(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY).getSetStrength());
    }
}
