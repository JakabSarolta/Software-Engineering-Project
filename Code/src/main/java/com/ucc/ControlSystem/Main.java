package com.ucc.ControlSystem;


import com.ucc.ControlSystem.ControlSystem.InputParameters.InputParameterProcessor;
import com.ucc.ControlSystem.ControlSystem.JDBC.ConnectionFactory;
import com.ucc.ControlSystem.GUI.AdminControlPanel;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main{
    private static AdminControlPanel adminFrame = AdminControlPanel.getAdminControlPanel();
    public static void main(String[] args) {

        String jdbUrl = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_URL);
        String username = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_USER);
        String pass = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_PASS);

        ConnectionFactory.createDbConnection(jdbUrl,username,pass);


        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setVisible(true);
        adminFrame.populateFields(InputParameterProcessor.getInputParameterProcessor().getParameters());
        adminFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                InputParameterProcessor.getInputParameterProcessor().persistParameters();
            }
        });
    }
}
