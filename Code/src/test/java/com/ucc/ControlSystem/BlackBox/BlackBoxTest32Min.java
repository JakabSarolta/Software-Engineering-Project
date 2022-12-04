package com.ucc.ControlSystem.BlackBox;

import com.ucc.ControlSystem.ControlSystem.JDBC.ConnectionFactory;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;
import com.ucc.ControlSystem.TestDriver.TestDriver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackBoxTest32Min {
    @Test
    public void blackBoxTest32Min() throws InterruptedException {
        String jdbUrl = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_URL);
        String username = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_USER);
        String pass = SystemConfigurationReader.getSystemConfigurationReader().
                readEnvironmentVariable(SystemConfigParameters.DATABASE_PASS);
        ConnectionFactory.createDbConnection(jdbUrl,username,pass);

        TestDriver.getInstance().startSimulation();
        Thread.sleep(16000);
        double airTemp = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.AIR_TEMPERATURE) * 10) / 10.0;
        double waterTemp = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.WATER_TEMPERATURE) * 10) / 10.0;
        double humidity = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.HUMIDITY) * 10) / 10.0;
        double ph_level = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.PH_LEVEL) * 100) / 100.0;
        double ec = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY) * 100) / 100.0;
        double waterLevel = Math.round(TestDriver.getInstance().getSensorValue(EnvironmentDeviceTypes.WATER_LEVEL) * 10) / 10.0;

//        System.out.println("AirTemp " + airTemp + "\nWaterTemp " + waterTemp + "\nHumidity " + humidity +
//                "\nph_level " + ph_level + "\nec " + ec + "\nwater level " + waterLevel);

        assertTrue(
                airTemp == 18.6
                        && waterTemp == 10.9
                        &&  humidity == 48.7
                        && ph_level == 6.56
                        && ec == 6.16
                        && waterLevel == 72.0
        );
    }
}
