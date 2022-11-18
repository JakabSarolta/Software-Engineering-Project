package com.ucc.ControlSystem;

import com.ucc.ControlSystem.ControlSystem.InputParameters.*;
import com.ucc.ControlSystem.ControlSystem.JDBC.ConnectionFactory;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigParameters;
import com.ucc.ControlSystem.SystemConfiguration.SystemConfigurationReader;

import javax.swing.*;

public class Main{
    private static JFrame frame = new EnvironmentControlPanel("Vertical Farm Control Simulation");

    public static void main(String[] args) {

        String jdbUrl = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_URL);
        String username = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_USER);
        String pass = SystemConfigurationReader.getSystemConfigurationReader().readEnvironmentVariable(SystemConfigParameters.DATABASE_PASS);

        ConnectionFactory.createDbConnection(jdbUrl,username,pass);


        InputParameterProcessor processor = InputParameterProcessor.getInputParameterProcessor();
//        processor.updateEnvironmentPropertyParameter(4,20, EnvironmentDeviceTypes.AIR_TEMPERATURE);
//        processor.updateMeasurementIntervalParameter(10,5,EnvironmentDeviceTypes.AIR_TEMPERATURE);
//        processor.updateOtherParameter(OtherParameters.GROWTH_TIME,600);
//        processor.persistParameters();
        for(InputParameter p : processor.getParameters()){
            if(p instanceof EnvironmentPropertyParameter){
                System.out.println(p.getType().toString() +" " + (((EnvironmentPropertyParameter) p).getMin()) + " " + ((EnvironmentPropertyParameter) p).getMax());
            }else if(p instanceof MeasurementIntervalParameter){
                System.out.println(p.getType() + " " + ((MeasurementIntervalParameter) p).getIntervalBalancedState() + " " + ((MeasurementIntervalParameter) p).getIntervalBalancingState());
            }else if(p instanceof OtherParameter){
                System.out.println(p.getType() + " " + ((OtherParameter) p).getValue());
            }
        }
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
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
