package com.ucc.ControlSystem.GUI;

import javax.swing.*;
import java.awt.*;

public class EnvironmentControlPanel extends JFrame {

    private JPanel contentPane;
    private JLabel titleLabel, sensorTendencyLabel, actuatorTendencyLabel, simulationTimeLabel, seconds1Label,
        saladSimulationTimeLabel, seconds2Label, temperatureNameLabel, temperatureValueLabel,
        timeNameLabel, timeValueLabel;
    private JPanel timePanel, simulationPanel;
    private JTextField simulationTimeTextField, saladSimulationTimeText;
    private JSlider sigma, alpha;
    private JButton startButton;
    EnvironmentControlPanelController environmentControlPanelController = new EnvironmentControlPanelController();

    public EnvironmentControlPanel(String title){
        super(title);
        prepareGUI();
    }

    public void prepareGUI(){
        this.setSize(600, 550);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(8,1));
        this.titleLabel = new JLabel("Temperature Controller", JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        this.contentPane.add(this.titleLabel);
        simulationPanel = new JPanel(new GridLayout(1,4));
        this.temperatureNameLabel = new JLabel("Temperature (Celsius)", JLabel.CENTER);
        simulationPanel.add(temperatureNameLabel);
        this.temperatureValueLabel = new JLabel("", JLabel.CENTER);
        simulationPanel.add(temperatureValueLabel);
        this.timeNameLabel = new JLabel("Current time (salad time)", JLabel.CENTER);
        simulationPanel.add(timeNameLabel);
        this.timeValueLabel = new JLabel("", JLabel.CENTER);
        simulationPanel.add(timeValueLabel);
        contentPane.add(simulationPanel);
        this.sensorTendencyLabel = new JLabel("Sensor Tendency (sigma)", JLabel.CENTER);
        contentPane.add(sensorTendencyLabel);
        sigma = createSlider(-5,5, 1);
        contentPane.add(sigma);
        this.actuatorTendencyLabel = new JLabel("Actuator Tendency (alpha)", JLabel.CENTER);
        contentPane.add(actuatorTendencyLabel);
        alpha = createSlider(-5,5, 1);
        contentPane.add(alpha);
        this.timePanel = new JPanel();
        this.timePanel.setLayout(new GridLayout(2,3));
        simulationTimeLabel = new JLabel("Simulation Time: ", JLabel.CENTER);
        timePanel.add(simulationTimeLabel);
        simulationTimeTextField = new JTextField();
        timePanel.add(simulationTimeTextField);
        seconds1Label = new JLabel("(in seconds)", JLabel.CENTER);
        timePanel.add(seconds1Label);
        saladSimulationTimeLabel = new JLabel("Salad Simulation Time: ", JLabel.CENTER);
        timePanel.add(saladSimulationTimeLabel);
        saladSimulationTimeText = new JTextField();
        timePanel.add(saladSimulationTimeText);
        seconds2Label = new JLabel("(in seconds)", JLabel.CENTER);
        timePanel.add(seconds2Label);
        contentPane.add(timePanel);
        startButton = new JButton("START");
        startButton.setActionCommand("Start the simulation");
        contentPane.add(startButton);
        this.setContentPane(this.contentPane);
    }

    public JSlider createSlider(int min, int max, int spacing){
        JSlider slider = new JSlider(min,max);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        java.util.Hashtable labelTable = new java.util.Hashtable();
        for(int i = min; i <= max; i++){
            labelTable.put(new Integer(i), new JLabel(i*0.1+""));
        }

        labelTable.put(new Integer(0), new JLabel("0"));
        if(max>=3){
            labelTable.put(new Integer(3), new JLabel("0.3"));
        }
        if(min<=-3){
            labelTable.put(new Integer(-3), new JLabel("-0.3"));
        }
        slider.setLabelTable(labelTable);
        slider.setMajorTickSpacing(spacing);
        slider.addChangeListener(e -> ((JSlider)e.getSource()).getValue());
        return slider;
    }
}
