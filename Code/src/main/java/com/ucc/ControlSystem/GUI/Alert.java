package com.ucc.ControlSystem.GUI;

import javax.swing.*;

public class Alert {
    public static void alert(String msg, String type, int messageType) {

        JFrame alertFrame = new JFrame("Alert!");

        JOptionPane.showMessageDialog(alertFrame,
                msg, type,
                messageType);
    }
}
