package com.ucc.ControlSystem.ControlParameters;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class updateCheckTimeTest {
    @Test
    public void testForTrue(){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(5,30, EnvironmentDeviceTypes.AIR_TEMPERATURE);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getBalancedCheckIntervalForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE) == 5 &&
                InputParameterProcessor.getInputParameterProcessor().getBalancingCheckIntervalForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE) == 30, "Balanced and balancing state checks are updated correctly");
    }


    @Test
    public void testForFalse(){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(5,30, EnvironmentDeviceTypes.AIR_TEMPERATURE);
        assertFalse(InputParameterProcessor.getInputParameterProcessor().getBalancedCheckIntervalForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE) != 5 &&
                InputParameterProcessor.getInputParameterProcessor().getBalancingCheckIntervalForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE) != 30, "Balanced and balancing state checks are NOT updated correctly");
    }
}
