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
    public void testForTrue(int param1, int param2, EnvironmentDeviceTypes param3){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(param1,param2, param3);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getBalancedCheckIntervalForDevice(param3) == param1 &&
                InputParameterProcessor.getInputParameterProcessor().getBalancingCheckIntervalForDevice(param3) == param2, "Balanced and balancing state checks are updated correctly");
    }

    private static ArrayList<Arguments> provideTrueInput(){
        ArrayList<Arguments> argumentList = new ArrayList<>();

        argumentList.add(Arguments.of(13, 15, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(12, 20, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(13, 15, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(12, 20, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(13, 15, EnvironmentDeviceTypes.HUMIDITY));
        argumentList.add(Arguments.of(12, 20, EnvironmentDeviceTypes.HUMIDITY));
        return argumentList;
    }

    @ParameterizedTest
    @MethodSource("provideFalseInput")
    public void testForFalse(int param1, int param2, EnvironmentDeviceTypes param3){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(param1,param2, param3);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getBalancedCheckIntervalForDevice(param3) != param1 &&
                InputParameterProcessor.getInputParameterProcessor().getBalancingCheckIntervalForDevice(param3) != param2, "Balanced and balancing state checks are NOT updated correctly");
    }

    private static ArrayList<Arguments> provideFalseInput(){
        ArrayList<Arguments> argumentList = new ArrayList<>();

        argumentList.add(Arguments.of(0, 15, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(-2,2, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(0, 15, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(-2,2, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(0, 15, EnvironmentDeviceTypes.HUMIDITY));
        argumentList.add(Arguments.of(-2,2, EnvironmentDeviceTypes.HUMIDITY));
        //it checks the same constraint for all device types
        return argumentList;
    }
}
