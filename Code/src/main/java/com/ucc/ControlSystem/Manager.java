package com.ucc.ControlSystem;

import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;

import javax.swing.*;
import java.math.BigDecimal;

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
            ((EnvironmentControlPanel)frame).getTemperatureValueLabel().setText(BigDecimal.valueOf(measurement)+"");
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
