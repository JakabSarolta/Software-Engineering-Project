package com.ucc.ControlSystem.GUI;

import com.ucc.ControlSystem.EnvironmentSimulator.Controller;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Alert {
    public static void alert(String msg,String type) {
        JFrame alertFrame = new JFrame();
        alertFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Controller.resumeSimulation();
            }
        });
        JOptionPane.showMessageDialog(alertFrame,
                msg, type,
                JOptionPane.ERROR_MESSAGE);
    }
}
