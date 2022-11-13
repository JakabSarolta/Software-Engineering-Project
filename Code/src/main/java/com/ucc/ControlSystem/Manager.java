package com.ucc.ControlSystem;

import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;
import com.ucc.ControlSystem.Utils.TimeConvertor;
import com.ucc.ControlSystem.Utils.TimeUnits;

import javax.swing.*;

public class Manager implements Runnable{

    private final JFrame frame;

    public Manager(JFrame frame) {
        this.frame = frame;
    }


    @Override
    public void run() {
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        // how many real-life seconds is one salad second
        double oneSaladSec = (((double)es.getDurationOfTheSimulationRealLifeTime())/es.getDurationOfTheSimulationSaladTime());
        // the same (in millis)
        int oneSaladMillis = (int)(oneSaladSec * 1000);

        // elapsed time from the beginning of the simulation
        long simulationTime = 0;

        // how many simulation time units have passed
        long i = 0;

        TimeUnits previousSelectedDisplayTimeUnit = (TimeUnits) ((EnvironmentControlPanel)frame).getDisplayTimeUnitComboBox().getSelectedItem();

        while(TimeConvertor.convertMillisToSeconds(simulationTime) <= es.getDurationOfTheSimulationRealLifeTime()){
            TimeUnits selectedDisplayTimeUnit = (TimeUnits) ((EnvironmentControlPanel)frame).getDisplayTimeUnitComboBox().getSelectedItem();

            i = adjustCurrentTimeToStep(previousSelectedDisplayTimeUnit,selectedDisplayTimeUnit,i);
            previousSelectedDisplayTimeUnit = selectedDisplayTimeUnit;

            double measurement = es.takeMeasurement(EnvironmentDeviceTypes.TEMPERATURE);
            ((EnvironmentControlPanel)frame).getTemperatureValueLabel().setText(Math.round(measurement*100)/100.0+"");
            ((EnvironmentControlPanel)frame).getTimeValueLabel().setText(i+"");

            i++;

            try {
                // the delay in order to change the displayed time by one salad time unit
                // how many milliseconds is one salad time unit
                Thread.sleep(oneSaladMillis * selectedDisplayTimeUnit.getVal());

                simulationTime += oneSaladMillis * selectedDisplayTimeUnit.getVal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long adjustCurrentTimeToStep(TimeUnits previousSelectedTimeUnit, TimeUnits  selectedTimeUnit, long i){
        if(previousSelectedTimeUnit != selectedTimeUnit){
            if(previousSelectedTimeUnit.getVal() < selectedTimeUnit.getVal()){
                // current simulation time divided by the ratio of the 2 units
                i  = Math.round(i / ((double)selectedTimeUnit.getVal())/ previousSelectedTimeUnit.getVal());
            }else {
                i  = i * Math.round(((double)previousSelectedTimeUnit.getVal())/selectedTimeUnit.getVal());
            }
        }
        return i;
    }
}




