package com.ucc.ControlSystem;

import com.ucc.ControlSystem.ControlSystem.JDBC.ConnectionFactory;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        String jdbUrl = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_URL);
        String username = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_USER);
        String pass = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_PASS);

        ConnectionFactory.createDbConnection(jdbUrl,username,pass);

        System.out.printf("Connection made!");
    }
}
