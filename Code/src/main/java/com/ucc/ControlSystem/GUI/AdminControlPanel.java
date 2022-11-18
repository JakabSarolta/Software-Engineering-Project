package com.ucc.ControlSystem.GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminControlPanel extends JFrame{
    final static String INITIALIZEPANEL = "Initialize/Update parameters";
    final static String MONITORPANEL = "Monitor system";
    final static String ALERTSPANEL = "Alerts";
    final static String REPORTSPANEL = "Reports";
    private final JPanel card1 = new JPanel();
    private final JPanel card2 = new JPanel();
    private final JPanel card3 = new JPanel();
    private final JPanel card4 = new JPanel();
    private final JPanel parameters = new JPanel();
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JButton updateButton = new JButton("      UPDATE       ");

    Font  f1  = new Font("Our font", Font.BOLD, 16);
    Font  f2  = new Font("Our font", Font.BOLD, 14);

    public AdminControlPanel(String title){
        super(title);
        prepareGUI();
    }

    public void prepareGUI(){
        this.setSize(580, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Initialize/Update System Parameters", JLabel.CENTER);
        titleLabel.setFont(f1);
        JLabel growthTimeLabel = new JLabel("Growth time:");
        growthTimeLabel.setFont(f2);
        JLabel minAirTempLabel = new JLabel("Min. air temperature:");
        minAirTempLabel.setFont(f2);
        JLabel maxAirTempLabel = new JLabel("Max. air temperature");
        maxAirTempLabel.setFont(f2);
        JLabel balanceTempCheckLabel = new JLabel("Temperature check for balanced state:");
        balanceTempCheckLabel.setFont(f2);
        JLabel balancingTempCheckLabel = new JLabel("Temperature check for balancing state:");
        balancingTempCheckLabel.setFont(f2);
        JTextField growthTime = new JTextField();
        JTextField minAirTemp = new JTextField();
        JTextField maxAirTemp = new JTextField();
        JTextField balancingTempCheck = new JTextField();
        JTextField balanceTempCheck = new JTextField();

        card1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.CENTER;
        card1.add(titleLabel, c);
        parameters.setLayout(new GridLayout(5, 2));
        parameters.add(growthTimeLabel);
        parameters.add(growthTime);
        parameters.add(minAirTempLabel);
        parameters.add(minAirTemp);
        parameters.add(maxAirTempLabel);
        parameters.add(maxAirTemp);
        parameters.add(balanceTempCheckLabel);
        parameters.add(balanceTempCheck);
        parameters.add(balancingTempCheckLabel);
        parameters.add(balancingTempCheck);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        card1.add(parameters, c);
        c.gridx = 0;
        c.gridy = 2;
        card1.add(new JLabel(""), c);
        updateButton.setBorder(new LineBorder(Color.BLACK));
        updateButton.setFont(f1);
        updateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        c.gridx = 0;
        c.gridy = 3;
        c.fill = GridBagConstraints.VERTICAL;
        card1.add(updateButton, c);
        c.gridx = 0;
        c.gridy = 4;
        card1.add(new JLabel(""), c);

        card2.setLayout(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();
        c2.weightx = 1;
        c2.weighty = 1;
        JLabel monitorTitle = new JLabel("General Overview of Current State");
        c2.gridx = 0;
        c2.gridy = 0;
        c2.gridwidth = 1;
        c2.fill = GridBagConstraints.CENTER;
        monitorTitle.setFont(f1);
        card2.add(monitorTitle, c2);
        JPanel currentValuesPanel = new JPanel();
        currentValuesPanel.setLayout(new GridLayout(4, 2));
        JLabel currentTimeLabel = new JLabel("Current time in growing phase:");
        currentTimeLabel.setFont(f1);
        currentValuesPanel.add(currentTimeLabel);
        JLabel currentTime = new JLabel("", JLabel.CENTER);
        currentTime.setFont(f1);
        currentValuesPanel.add(currentTime);
        JLabel currentTempLabel = new JLabel("Current Temperature:");
        currentTempLabel.setFont(f1);
        currentValuesPanel.add(currentTempLabel);
        JLabel currentTemp = new JLabel("", JLabel.CENTER);
        currentTemp.setFont(f1);
        currentValuesPanel.add(currentTemp);
        JLabel actuatorStateLabel = new JLabel("Actuator state:");
        actuatorStateLabel.setFont(f1);
        currentValuesPanel.add(actuatorStateLabel);
        JLabel actuatorState = new JLabel("Off", JLabel.CENTER);
        actuatorState.setFont(f1);
        currentValuesPanel.add(actuatorState);
        JLabel currentStateLabel = new JLabel("Current state of the system:");
        currentStateLabel.setFont(f1);
        currentValuesPanel.add(currentStateLabel);
        JLabel currentState = new JLabel("", JLabel.CENTER);
        currentState.setFont(f1);
        currentValuesPanel.add(currentState);
        c2.gridy = 1;
        card2.add(currentValuesPanel, c2);
        c2.gridy = 2;
        card2.add(new JLabel(""), c2);


        tabbedPane.addTab(INITIALIZEPANEL, card1);
        tabbedPane.addTab(MONITORPANEL, card2);
        tabbedPane.addTab(ALERTSPANEL, card3);
        tabbedPane.addTab(REPORTSPANEL, card4);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setContentPane(this.tabbedPane);
    }
}
