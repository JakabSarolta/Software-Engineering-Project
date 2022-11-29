package com.ucc.ControlSystem.GUI;

import javax.swing.*;

public class Alert {
    public static void alert(String msg, String type){
        JOptionPane.showMessageDialog(new JFrame(), msg, type, JOptionPane.ERROR_MESSAGE);
    }
}
