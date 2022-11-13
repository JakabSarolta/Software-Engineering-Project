package com.ucc.ControlSystem;

import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;
import com.ucc.ControlSystem.Utils.TimeConvertor;
import com.ucc.ControlSystem.Utils.TimeUnits;

import javax.swing.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Manager implements Runnable{

    private final JFrame frame;

    public Manager(JFrame frame) {
        this.frame = frame;
    }


    @Override
    public void run() {
        int time = 1;

        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        // how many real-life seconds is one salad second (in millis)
        double oneSaladSec = (((double)es.getDurationOfTheSimulationRealLifeTime())/es.getDurationOfTheSimulationSaladTime());
        int oneSaladMillis = (int)(oneSaladSec * 1000);

        Timestamp beforeSleep,afterSleep;
        long simulationTime = 0;

        long i = 0;

        TimeUnits previousSelectedTimeUnit = (TimeUnits) ((EnvironmentControlPanel)frame).getDisplayTimeUnitComboBox().getSelectedItem();

        while(TimeConvertor.convertMillisToSeconds(simulationTime) <= es.getDurationOfTheSimulationRealLifeTime()){
            TimeUnits selectedTimeUnit = (TimeUnits) ((EnvironmentControlPanel)frame).getDisplayTimeUnitComboBox().getSelectedItem();

            i = adjustCurrentTimeToStep(previousSelectedTimeUnit,selectedTimeUnit,i);
            previousSelectedTimeUnit = selectedTimeUnit;
            
            double measurement = es.takeMeasurement(EnvironmentDeviceTypes.TEMPERATURE);
            ((EnvironmentControlPanel)frame).getTemperatureValueLabel().setText(Math.round(measurement*100)/100.0+"");
            ((EnvironmentControlPanel)frame).getTimeValueLabel().setText(i+"");

            i++;

            beforeSleep = Timestamp.valueOf(LocalDateTime.now());
            try {
                Thread.sleep(oneSaladMillis * selectedTimeUnit.getVal());

                afterSleep = Timestamp.valueOf(LocalDateTime.now());
                simulationTime += (afterSleep.getTime() - beforeSleep.getTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long adjustCurrentTimeToStep(TimeUnits previousSelectedTimeUnit, TimeUnits  selectedTimeUnit, long i){
        if(previousSelectedTimeUnit != selectedTimeUnit){
            if(previousSelectedTimeUnit.getVal() < selectedTimeUnit.getVal()){
                i  = Math.round(i / ((double)selectedTimeUnit.getVal())/ previousSelectedTimeUnit.getVal());
            }else {
                i  = i * Math.round(((double)previousSelectedTimeUnit.getVal())/selectedTimeUnit.getVal());
            }
        }
        return i;
    }
}
