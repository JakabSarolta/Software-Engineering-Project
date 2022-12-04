package com.ucc.ControlSystem.TemperatureControl;

import com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.Controller;
import com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.EnvironmentBalancer;
import com.ucc.ControlSystem.ControlSystem.InputParameters.EnvironmentPropertyParameter;
import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;
import com.ucc.ControlSystem.Main;
import com.ucc.ControlSystem.Utils.ConsoleDisplayAdapterInterfaceIml;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsActuatorOnTest {
    @Test
    public void isActuatorOnTrueTest(){
        Main.main(null);
        EnvironmentPropertyParameter environmentPropertyParameter = new EnvironmentPropertyParameter();

        environmentPropertyParameter.setMin(16);
        environmentPropertyParameter.setMax(20);
        EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                put(EnvironmentDeviceTypes.AIR_TEMPERATURE,14.0);

        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(
                30*60, 5*60, EnvironmentDeviceTypes.AIR_TEMPERATURE);

        Controller controller = new Controller(new ConsoleDisplayAdapterInterfaceIml());

        controller.timePassed(2*30*60);
        controller.timePassed(65*60);

        assertTrue(EnvironmentBalancer.getEnvironmentBalancer(controller.getDisplayAdapter()).
                isActuatorOn(EnvironmentDeviceTypes.AIR_TEMPERATURE));
    }
    @Test
    public void isActuatorOnFalseTest(){
        Main.main(null);
        EnvironmentPropertyParameter environmentPropertyParameter = new EnvironmentPropertyParameter();

        environmentPropertyParameter.setMin(16);
        environmentPropertyParameter.setMax(20);
        EnvironmentSimulator.getEnvironmentSimulator().getLastMeasuredValues().
                put(EnvironmentDeviceTypes.AIR_TEMPERATURE,17.0);

        InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(
                30*60, 5*3600, EnvironmentDeviceTypes.AIR_TEMPERATURE);

        Controller controller = new Controller(new ConsoleDisplayAdapterInterfaceIml());

        controller.timePassed(2*30*60);
        controller.timePassed(65*60);

        assertFalse(EnvironmentBalancer.getEnvironmentBalancer(controller.getDisplayAdapter()).
                isActuatorOn(EnvironmentDeviceTypes.AIR_TEMPERATURE));
    }
}
