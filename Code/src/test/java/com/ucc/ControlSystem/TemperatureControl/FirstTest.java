package com.ucc.ControlSystem.TemperatureControl;

import com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.Sentinel;
import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.ControlSystem.InputParameters.OtherParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FirstTest {
    @Test
    public void firstTest() {

        InputParameterProcessor.getInputParameterProcessor().
                updateOtherParameter(OtherParameters.GROWTH_TIME, 30);
        boolean due = Sentinel.getSentinel().isGrowthTimeDue(30*36400);
        assertTrue(due);
//        String result = Controller.convertSeconds(12300);
//        assertTrue(String.valueOf(result).equals("3 hours 25 minutes"));
    }
}
