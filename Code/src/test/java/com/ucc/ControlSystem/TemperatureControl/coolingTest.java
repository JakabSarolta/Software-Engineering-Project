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

public class coolingTest {
    @Test
    public void coolingTest() throws InterruptedException {
        Main.main(null);
        EnvironmentPropertyParameter environmentPropertyParameter = new EnvironmentPropertyParameter();

        environmentPropertyParameter.setMin(16);
        environmentPropertyParameter.setMax(20);
        EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                put(EnvironmentDeviceTypes.AIR_TEMPERATURE,21.0);

        EnvironmentSimulator.getEnvironmentSimulator().setSensorTendency(
                EnvironmentDeviceTypes.AIR_TEMPERATURE,-0.1);

        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(
                30*60, 5*60, EnvironmentDeviceTypes.AIR_TEMPERATURE);

        for(int i = 0; i <= 10; i++){
            Controller.getController().timePassed(30*60+i);
            TimeUnit.SECONDS.sleep(1);
        }

        assertTrue(Math.round(EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                get(EnvironmentDeviceTypes.AIR_TEMPERATURE)) == 20.0);
    }
}
