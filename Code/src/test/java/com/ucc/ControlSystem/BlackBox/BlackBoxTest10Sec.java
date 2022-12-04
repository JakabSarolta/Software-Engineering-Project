package com.ucc.ControlSystem.BlackBox;

import com.ucc.ControlSystem.ControlSystem.JDBC.ConnectionFactory;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;
import com.ucc.ControlSystem.TestDriver.TestDriver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackBoxTest10Sec {
    @Test
    public void blackBoxTest10Sec() throws InterruptedException {
        String jdbUrl = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_URL);
        String username = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_USER);
        String pass = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_PASS);
        ConnectionFactory.createDbConnection(jdbUrl,username,pass);

        TestDriver.getInstance().startSimulation();
        Thread.sleep(10*1000);
        double airTemp = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.AIR_TEMPERATURE) * 10) / 10.0;
        double waterTemp = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.WATER_TEMPERATURE) * 10) / 10.0;
        double humidity = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.HUMIDITY) * 10) / 10.0;
        double ph_level = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.PH_LEVEL) * 100) / 100.0;
        double ec = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY) * 100) / 100.0;
        double waterLevel = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.WATER_LEVEL) * 10 / 10.0);

        assertTrue(
                airTemp == 18.0
                        && waterTemp == 11.5
                &&  humidity== 49.0
                && ph_level == 6.5
                && ec == 6.1
                && waterLevel == 75.0
        );
    }
}
