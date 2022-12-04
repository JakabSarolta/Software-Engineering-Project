package com.ucc.ControlSystem.BlackBox;

import com.ucc.ControlSystem.ControlSystem.JDBC.ConnectionFactory;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;
import com.ucc.ControlSystem.TestDriver.TestDriver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackBoxBalancedTest {
    @Test
    public void BlackBoxBalancedTest() throws InterruptedException {
        String jdbUrl = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_URL);
        String username = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_USER);
        String pass = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_PASS);
        ConnectionFactory.createDbConnection(jdbUrl,username,pass);

        TestDriver.getInstance().startSimulation();
        Thread.sleep(20000);
        double humidity = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.HUMIDITY) * 10) / 10.0;

        assertTrue(humidity == 49.1);
    }
}
