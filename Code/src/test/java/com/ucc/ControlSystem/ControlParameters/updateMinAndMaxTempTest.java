package com.ucc.ControlSystem.ControlParameters;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class updateMinAndMaxTempTest {
    @Test
    public void testForTrue() {
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(14,19, EnvironmentDeviceTypes.AIR_TEMPERATURE);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getEnvironmentPropertyForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE).getMin() == 14 &&
                InputParameterProcessor.getInputParameterProcessor().getEnvironmentPropertyForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE).getMax() == 19, "Min and max temperature are updated correctly");
    }

    @Test
    public void testForFalse(){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(14,19, EnvironmentDeviceTypes.AIR_TEMPERATURE);
        assertFalse(InputParameterProcessor.getInputParameterProcessor().getEnvironmentPropertyForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE).getMin() == 14 &&
                InputParameterProcessor.getInputParameterProcessor().getEnvironmentPropertyForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE).getMax() == 20, "Min and max temperature are NOT updated correctly");
    }
}
