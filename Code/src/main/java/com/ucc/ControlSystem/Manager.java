package com.ucc.ControlSystem;

import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;

import javax.swing.*;

public class Manager implements Runnable{

    private final JFrame frame;

    public Manager(JFrame frame) {
        this.frame = frame;
    }


    @Override
    public void run() {
        int time = 1;

        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();

        while(time <= es.getDurationOfTheSimulationRealLifeTime()){
            double measurement = es.takeMeasurement(EnvironmentDeviceTypes.TEMPERATURE);
            ((EnvironmentControlPanel)frame).getTemperatureValueLabel().setText(Math.round(measurement*100)/100.0+"");
            ((EnvironmentControlPanel)frame).getTimeValueLabel().setText(time+"");

            time++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
