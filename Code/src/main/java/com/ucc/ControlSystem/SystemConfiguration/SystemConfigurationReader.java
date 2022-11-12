package com.ucc.ControlSystem.SystemConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SystemConfigurationReader {

    private static SystemConfigurationReader configurationReader = null;

    private final Properties properties;
    private final String configurationFileName = "src/main/resources/app.conf";

    private SystemConfigurationReader() {
        this.properties = loadFile(configurationFileName);
    }

    // Ensure that this class is singleton
    public static SystemConfigurationReader getSystemConfigurationReader(){
        if(configurationReader == null){
            configurationReader = new SystemConfigurationReader();
        }
        return configurationReader;
    }

    // reads the value from the configuration file corresponding to the given SystemConfigParameter
    public String readParam(SystemConfigParameters param){
        return properties.getProperty(param.getParamValue());
    }

    // resolves the value of an environment variable corresponding whose name is given in the configuration file
    public String readEnvironmentVariable(SystemConfigParameters param){
        return System.getenv(properties.getProperty(param.getParamValue()));
    }

    private Properties loadFile(String configurationFileName) {
        Properties properties = new Properties();
        try{
            FileInputStream fs = new FileInputStream(configurationFileName);
            properties.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
