package com.ucc.ControlSystem.EnvironmentSimulator;

import com.ucc.ControlSystem.ControlSystem.Reporting.ReportGenerator;
import com.ucc.ControlSystem.GUI.Alert;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.Utils.TimeUnits;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that runs the environment simulation.
 */
public class Controller implements Runnable{

    private final JFrame frame;
    private static boolean exit = false;
    private static Controller manager;

    public Controller(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Static class that starts the simulation by saving the values entered into the GUI,
     * and by creating a new thread for the simulation (to run in parallel with the GUI).
     * @param frame
     * @param durationOfTheSimulationSaladTime
     * @param durationOfTheSimulationRealLifeTime
     */
    public static void startSimulation(JFrame frame, int durationOfTheSimulationSaladTime,
                                       int durationOfTheSimulationRealLifeTime){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();

        es.setDurationOfTheSimulationSaladTime(durationOfTheSimulationSaladTime);
        es.setDurationOfTheSimulationRealLifeTime(durationOfTheSimulationRealLifeTime);

        SwingWorker<Void,Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                manager = new Controller(frame);
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

    /**
     * The main thread that simulates the change of time. It runs the while loop
     * every second (in the life of a salad). Each time makes a measurement, calls
     * the control system and displays the values to the environment simulation control
     * panel.
     */
    @Override
    public void run() {
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        // how many real-life seconds is one salad second
        double oneSaladSec = (((double)es.getDurationOfTheSimulationRealLifeTime()) /
                es.getDurationOfTheSimulationSaladTime());
        // the same (in millis)
        int oneSaladMillis = (int)(oneSaladSec * 1000.0);
        int remainingNanos = (int) ((((oneSaladSec * 1000)) - oneSaladMillis)*1000000);
        // elapsed time from the beginning of the simulation
        double simulationTime = 0;

        // how many simulation time units have passed
        long i = 0;

        while(simulationTime <= es.getDurationOfTheSimulationRealLifeTime() && !exit){
            TimeUnits selectedDisplayTimeUnit = (TimeUnits) ((EnvironmentControlPanel)frame).getDisplayTimeUnitComboBox().getSelectedItem();


            Map<EnvironmentDeviceTypes,Double> measurements = es.takeMeasurements();
            for(EnvironmentDeviceTypes type : measurements.keySet()){
                EnvironmentControlPanel.deviceToLabelMap.get(type).setText(Math.round(measurements.get(type)*100)/100.0+"");
            }

            ((EnvironmentControlPanel)frame).getTimeValueLabel().
                    setText(adjustCurrentTimeToStep(selectedDisplayTimeUnit,i)+"");

            i++;

            com.ucc.ControlSystem.ControlSystem.EnvironmentControllers.Controller.
                    getController().timePassed(i);

            try {
                // the delay in order to change the displayed time by one salad time unit
                // how many milliseconds is one salad time unit
                Thread.sleep(oneSaladMillis,remainingNanos);

                simulationTime += oneSaladSec;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Alert.alert("You can collect the crops now.", "Growth time reached",
                JOptionPane.INFORMATION_MESSAGE);
    }



    private long adjustCurrentTimeToStep(TimeUnits  selectedTimeUnit, long i){
        return Math.round(Math.floor( ((double) i) / selectedTimeUnit.getVal()));
    }
}




