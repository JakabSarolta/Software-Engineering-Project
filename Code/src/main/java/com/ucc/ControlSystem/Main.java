package com.ucc.ControlSystem;

import com.ucc.ControlSystem.GUI.AdminControlPanel;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;

import javax.swing.*;

public class Main{
    private static JFrame frame = new EnvironmentControlPanel("Vertical Farm Control Simulation");
    private static JFrame adminFrame = new AdminControlPanel("Administrator Control");
    public static void main(String[] args) {

//        String jdbUrl = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_URL);
//        String username = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_USER);
//        String pass = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_PASS);
//
//        ConnectionFactory.createDbConnection(jdbUrl,username,pass);
//



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setVisible(true);
    }

    public static void startSimulation(int durationOfTheSimulationSaladTime, int durationOfTheSimulationRealLifeTime){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();

        es.setDurationOfTheSimulationSaladTime(durationOfTheSimulationSaladTime);
        es.setDurationOfTheSimulationRealLifeTime(durationOfTheSimulationRealLifeTime);

        SwingWorker<Void,Void> swingWorker = new SwingWorker<Void, Void>    () {
            @Override
            protected Void doInBackground() throws Exception {
                Manager manager = new Manager(frame);

                manager.run();
                return null;
            }
        };

        swingWorker.execute();
    }
}
