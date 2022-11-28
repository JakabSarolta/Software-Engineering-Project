package com.ucc.ControlSystem.SimulationEnvironment;

import com.ucc.ControlSystem.ControlSystem.Reporting.ReportGenerator;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.Utils.TimeConvertor;
import com.ucc.ControlSystem.Utils.TimeUnits;

import javax.swing.*;

public class Controller implements Runnable{

    private final JFrame frame;
    private static boolean exit = false;

    public Controller(JFrame frame) {
        this.frame = frame;
    }

    public static void startSimulation(JFrame frame,int durationOfTheSimulationSaladTime, int durationOfTheSimulationRealLifeTime){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();

        es.setDurationOfTheSimulationSaladTime(durationOfTheSimulationSaladTime);
        es.setDurationOfTheSimulationRealLifeTime(durationOfTheSimulationRealLifeTime);

        SwingWorker<Void,Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Controller manager = new Controller(frame);
                exit = false;

                manager.run();
                return null;
            }
        };

        swingWorker.execute();
    }

    public static void stopSimulation() {
        exit = true;
    }


    @Override
    public void run() {

        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        // how many real-life seconds is one salad second
        double oneSaladSec = (((double)es.getDurationOfTheSimulationRealLifeTime())/es.getDurationOfTheSimulationSaladTime());
        // the same (in millis)
        int oneSaladMillis = (int)(oneSaladSec * 1000.0);
        int remainingNanos = (int) ((((oneSaladSec * 1000)) - oneSaladMillis)*1000000);
        // elapsed time from the beginning of the simulation
        double simulationTime = 0;

        // how many simulation time units have passed
        long i = 0;

        while(simulationTime <= es.getDurationOfTheSimulationRealLifeTime() && !exit){
            TimeUnits selectedDisplayTimeUnit = (TimeUnits) ((EnvironmentControlPanel)frame).getDisplayTimeUnitComboBox().getSelectedItem();

            double measurement = es.takeMeasurement(EnvironmentDeviceTypes.AIR_TEMPERATURE);
            ((EnvironmentControlPanel)frame).getTemperatureValueLabel().setText(Math.round(measurement*100)/100.0+"");
            ((EnvironmentControlPanel)frame).getTimeValueLabel().setText(adjustCurrentTimeToStep(selectedDisplayTimeUnit,i)+"");

            i++;

            com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.Controller.getController().timePassed(i);

            try {
                // the delay in order to change the displayed time by one salad time unit
                // how many milliseconds is one salad time unit
                Thread.sleep(oneSaladMillis,remainingNanos);

                simulationTime += oneSaladSec;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ReportGenerator.getReportGenerator().generateReport(0);
    }



    private long adjustCurrentTimeToStep(TimeUnits  selectedTimeUnit, long i){
        return Math.round(Math.floor( ((double) i) / selectedTimeUnit.getVal()));
    }
}




