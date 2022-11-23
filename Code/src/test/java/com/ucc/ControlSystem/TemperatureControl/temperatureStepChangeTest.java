package com.ucc.ControlSystem.TemperatureControl;

import com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.Controller;
import com.ucc.ControlSystem.ControlSystem.InputParameters.EnvironmentPropertyParameter;
import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class temperatureStepChangeTest {
    @Test
    public void temperatureStepChangeTest() throws InterruptedException {
        Main.main(null);
        EnvironmentPropertyParameter environmentPropertyParameter = new EnvironmentPropertyParameter();

        environmentPropertyParameter.setMin(16);
        environmentPropertyParameter.setMax(20);
        EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                put(EnvironmentDeviceTypes.AIR_TEMPERATURE,14.0);

        EnvironmentSimulator.getEnvironmentSimulator().setSensorTendency(
                EnvironmentDeviceTypes.AIR_TEMPERATURE,-0.1);
        EnvironmentSimulator.getEnvironmentSimulator().setActuatorCurrentStrength(
                EnvironmentDeviceTypes.AIR_TEMPERATURE, 0.2);

        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(
                30*60, 5*60, EnvironmentDeviceTypes.AIR_TEMPERATURE);

        for(int i = 0; i <= 10; i++){
            Controller.getController().timePassed((30+i)*60);
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                get(EnvironmentDeviceTypes.AIR_TEMPERATURE));
        assertTrue(Math.round(EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                get(EnvironmentDeviceTypes.AIR_TEMPERATURE)) == 15.0);
    }
}
