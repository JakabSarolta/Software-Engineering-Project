package com.ucc.ControlSystem.TemperatureControl;

import com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.Sentinel;
import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.ControlSystem.InputParameters.OtherParameters;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrowthTimeDueTest {
    @Test
    public void growthTimeDueTrueTest() {
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().
                updateOtherParameter(OtherParameters.GROWTH_TIME, 30 * 86400);
        boolean due = Sentinel.getSentinel().isGrowthTimeDue(30 * 86400);
        assertTrue(due);
    }
    @Test
    public void growthTimeDueFalseTest() {
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().
                updateOtherParameter(OtherParameters.GROWTH_TIME, 30 * 86400);
        boolean due = Sentinel.getSentinel().isGrowthTimeDue(29 * 86400);
        assertFalse(due);
    }
}
