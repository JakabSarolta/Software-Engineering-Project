package com.ucc.ControlSystem.TemperatureControl;

import com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.Controller;
import com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.EnvironmentBalancer;
import com.ucc.ControlSystem.ControlSystem.InputParameters.EnvironmentPropertyParameter;
import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;
import com.ucc.ControlSystem.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class isActuatorOnTest {
    @Test
    public void isActuatorOnTest1(){
        Main.main(null);
        EnvironmentPropertyParameter environmentPropertyParameter = new EnvironmentPropertyParameter();

        environmentPropertyParameter.setMin(16);
        environmentPropertyParameter.setMax(20);
        EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                put(EnvironmentDeviceTypes.AIR_TEMPERATURE,14.0);

        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(
                30*60, 5*60, EnvironmentDeviceTypes.AIR_TEMPERATURE);

        Controller.getController().timePassed(2*30*60);
        Controller.getController().timePassed(65*60);

        assertTrue(EnvironmentBalancer.getEnvironmentBalancer().
                isActuatorOn(EnvironmentDeviceTypes.AIR_TEMPERATURE));
    }
    @Test
    public void isActuatorOnTest2(){
        Main.main(null);
        EnvironmentPropertyParameter environmentPropertyParameter = new EnvironmentPropertyParameter();

        environmentPropertyParameter.setMin(16);
        environmentPropertyParameter.setMax(20);
        EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                put(EnvironmentDeviceTypes.AIR_TEMPERATURE,17.0);

        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(
                30*60, 5*3600, EnvironmentDeviceTypes.AIR_TEMPERATURE);

        Controller.getController().timePassed(2*30*60);
        Controller.getController().timePassed(65*60);

        assertTrue(EnvironmentBalancer.getEnvironmentBalancer().
                isActuatorOn(EnvironmentDeviceTypes.AIR_TEMPERATURE));
    }
}
