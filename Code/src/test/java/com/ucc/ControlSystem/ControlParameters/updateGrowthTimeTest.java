package com.ucc.ControlSystem.ControlParameters;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.ControlSystem.InputParameters.OtherParameters;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class updateGrowthTimeTest {
    @ParameterizedTest
    @MethodSource("provideTrueInput")
    public void testForTrue(int param1) {
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateOtherParameter(OtherParameters.GROWTH_TIME, param1);
        assertEquals(param1, InputParameterProcessor.getInputParameterProcessor().getTotalGrowthTime().getValue(), "Growth time is updated correctly");
    }

    private static ArrayList<Arguments> provideTrueInput(){
        ArrayList<Arguments> argumentList = new ArrayList<>();

        argumentList.add(Arguments.of(13));
        argumentList.add(Arguments.of(20));
        argumentList.add(Arguments.of(0));
        return argumentList;
    }

    @ParameterizedTest
    @MethodSource("provideFalseInput")
    public void testForFalse(int param1){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateOtherParameter(OtherParameters.GROWTH_TIME, param1);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getTotalGrowthTime().getValue() != param1, "Growth time is NOT updated correctly");
    }

    private static ArrayList<Arguments> provideFalseInput(){
        ArrayList<Arguments> argumentList = new ArrayList<>();

        argumentList.add(Arguments.of(-100));
        argumentList.add(Arguments.of(-2));
        return argumentList;
    }
}
