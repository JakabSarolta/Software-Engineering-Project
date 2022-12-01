package com.ucc.ControlSystem.ControlParameters;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class updateCheckTimeTest {
    @ParameterizedTest
    @MethodSource("provideTrueInput")
    public void testForTrue(int param1, int param2){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(param1,param2, EnvironmentDeviceTypes.AIR_TEMPERATURE);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getBalancedCheckIntervalForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE) == param1 &&
                InputParameterProcessor.getInputParameterProcessor().getBalancingCheckIntervalForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE) == param2, "Balanced and balancing state checks are updated correctly");
    }

    private static ArrayList<Arguments> provideTrueInput(){
        ArrayList<Arguments> argumentList = new ArrayList<>();

        argumentList.add(Arguments.of(13, 15));
        argumentList.add(Arguments.of(12, 20));
        return argumentList;
    }

    @ParameterizedTest
    @MethodSource("provideFalseInput")
    public void testForFalse(int param1, int param2){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(param1,param2, EnvironmentDeviceTypes.AIR_TEMPERATURE);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getBalancedCheckIntervalForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE) != param1 &&
                InputParameterProcessor.getInputParameterProcessor().getBalancingCheckIntervalForDevice(EnvironmentDeviceTypes.AIR_TEMPERATURE) != param2, "Balanced and balancing state checks are NOT updated correctly");
    }

    private static ArrayList<Arguments> provideFalseInput(){
        ArrayList<Arguments> argumentList = new ArrayList<>();

        argumentList.add(Arguments.of(0, 15));
        argumentList.add(Arguments.of(-2,2));
        return argumentList;
    }
}
