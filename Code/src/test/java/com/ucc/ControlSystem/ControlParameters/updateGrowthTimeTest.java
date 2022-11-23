package com.ucc.ControlSystem.ControlParameters;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.ControlSystem.InputParameters.OtherParameters;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class updateGrowthTimeTest {
    @Test
    public void testForTrue() {
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateOtherParameter(OtherParameters.GROWTH_TIME, 15);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getTotalGrowthTime().getValue() == 15, "Growth time is updated correctly");
    }

    @Test
    public void testForFalse(){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateOtherParameter(OtherParameters.GROWTH_TIME, 15);
        assertFalse(InputParameterProcessor.getInputParameterProcessor().getTotalGrowthTime().getValue() == 20, "Growth time is NOT updated correctly");
    }
}
