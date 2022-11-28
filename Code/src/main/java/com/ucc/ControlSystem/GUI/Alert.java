package com.ucc.ControlSystem.GUI;

import javax.swing.*;

public class Alert {
    public static void alert(String msg){
        JOptionPane.showMessageDialog(new JFrame(),
                msg, "ALERT",
                JOptionPane.ERROR_MESSAGE);
    }
}
