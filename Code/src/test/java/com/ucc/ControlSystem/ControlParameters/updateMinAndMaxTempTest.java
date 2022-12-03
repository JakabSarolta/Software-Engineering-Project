package com.ucc.ControlSystem.ControlParameters;

import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class updateMinAndMaxTempTest {
    @ParameterizedTest
    @MethodSource("provideTrueInput")
    public void testForTrue(double param1, double param2, EnvironmentDeviceTypes param3) {
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(param1,param2, param3);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getEnvironmentPropertyForDevice(param3).getMin() == param1 &&
                InputParameterProcessor.getInputParameterProcessor().getEnvironmentPropertyForDevice(param3).getMax() == param2, "Min and max temperature are updated correctly");
    }

    private static ArrayList<Arguments> provideTrueInput(){
        ArrayList<Arguments> argumentList = new ArrayList<>();

        argumentList.add(Arguments.of(13, 15, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(0, 30, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(13, 15, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(4, 30, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(13, 15, EnvironmentDeviceTypes.HUMIDITY));
        argumentList.add(Arguments.of(0, 100, EnvironmentDeviceTypes.HUMIDITY));
        argumentList.add(Arguments.of(13, 15, EnvironmentDeviceTypes.WATER_LEVEL));
        argumentList.add(Arguments.of(0, 30, EnvironmentDeviceTypes.WATER_LEVEL));
        argumentList.add(Arguments.of(6, 7, EnvironmentDeviceTypes.PH_LEVEL));
        argumentList.add(Arguments.of(0, 14, EnvironmentDeviceTypes.PH_LEVEL));
        argumentList.add(Arguments.of(1.5, 2.25, EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY));
        argumentList.add(Arguments.of(0, 9.6, EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY));
        return argumentList;
    }

    @ParameterizedTest
    @MethodSource("provideFalseInput")
    public void testForFalse(double param1, double param2, EnvironmentDeviceTypes param3){
        Main.main(null);
        InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(param1, param2, param3);
        assertTrue(InputParameterProcessor.getInputParameterProcessor().getEnvironmentPropertyForDevice(param3).getMin() != param1 ||
                InputParameterProcessor.getInputParameterProcessor().getEnvironmentPropertyForDevice(param3).getMax() != param2, "Min and max temperature are NOT updated correctly");
    }

    private static ArrayList<Arguments> provideFalseInput(){
        ArrayList<Arguments> argumentList = new ArrayList<>();

        argumentList.add(Arguments.of(-100, 12, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(-2, -24, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(2, -24, EnvironmentDeviceTypes.AIR_TEMPERATURE));
        argumentList.add(Arguments.of(-100, 12, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(-2, -24, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(2, -24, EnvironmentDeviceTypes.WATER_TEMPERATURE));
        argumentList.add(Arguments.of(-100, 12, EnvironmentDeviceTypes.HUMIDITY));
        argumentList.add(Arguments.of(-2, -24, EnvironmentDeviceTypes.HUMIDITY));
        argumentList.add(Arguments.of(2, -24, EnvironmentDeviceTypes.HUMIDITY));
        argumentList.add(Arguments.of(-100, 12, EnvironmentDeviceTypes.WATER_LEVEL));
        argumentList.add(Arguments.of(-2, -24, EnvironmentDeviceTypes.WATER_LEVEL));
        argumentList.add(Arguments.of(2, -24, EnvironmentDeviceTypes.WATER_LEVEL));
        argumentList.add(Arguments.of(-100, 12, EnvironmentDeviceTypes.PH_LEVEL));
        argumentList.add(Arguments.of(-2, -24, EnvironmentDeviceTypes.PH_LEVEL));
        argumentList.add(Arguments.of(2, -24, EnvironmentDeviceTypes.PH_LEVEL));
        argumentList.add(Arguments.of(-100, 12, EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY));
        argumentList.add(Arguments.of(-2, -24, EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY));
        argumentList.add(Arguments.of(2, -24, EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY));
        return argumentList;
    }
}
