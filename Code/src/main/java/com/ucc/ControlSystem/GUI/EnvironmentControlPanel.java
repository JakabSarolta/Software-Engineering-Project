package com.ucc.ControlSystem.GUI;

import com.ucc.ControlSystem.Main;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EnvironmentControlPanel extends JFrame {

    private JPanel contentPane;
    private JLabel titleLabel, sensorTendencyLabel, actuatorTendencyLabel, simulationTimeLabel, seconds1Label,
        saladSimulationTimeLabel, temperatureNameLabel, temperatureValueLabel,
        timeNameLabel, timeValueLabel;
    private JPanel timePanel, simulationPanel;
    private JTextField simulationTimeTextField, saladSimulationTimeText;
    private JSlider sigma, alpha;
    private JButton startButton;
    private JComboBox timeUnitsComboBox, displayTimeUnitComboBox;

    private final double SLIDER_SCALE = 10.0;

    public EnvironmentControlPanel(String title){
        super(title);
        prepareGUI();
    }

    public void prepareGUI(){
        this.setSize(780, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(8,1));
        this.titleLabel = new JLabel("Temperature Controller", JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        this.contentPane.add(this.titleLabel);
        simulationPanel = new JPanel(new GridLayout(1,5));
        this.temperatureNameLabel = new JLabel("Temperature (Celsius)", JLabel.CENTER);
        simulationPanel.add(temperatureNameLabel);
        this.temperatureValueLabel = new JLabel("", JLabel.CENTER);
        simulationPanel.add(temperatureValueLabel);
        this.timeNameLabel = new JLabel("Current time (salad time)", JLabel.CENTER);
        simulationPanel.add(timeNameLabel);
        this.timeValueLabel = new JLabel("", JLabel.CENTER);
        simulationPanel.add(timeValueLabel);
        String timeUnits[] = new String[]{"seconds", "minutes", "hours", "days"};
        displayTimeUnitComboBox = new JComboBox(timeUnits);
        simulationPanel.add(displayTimeUnitComboBox);
        contentPane.add(simulationPanel);
        this.sensorTendencyLabel = new JLabel("Sensor Tendency (sigma)", JLabel.CENTER);
        contentPane.add(sensorTendencyLabel);
        sigma = createSlider(-5,5, 1);
        sigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(sigma.getValue());
            }
        });
        contentPane.add(sigma);
        this.actuatorTendencyLabel = new JLabel("Actuator Tendency (alpha)", JLabel.CENTER);
        contentPane.add(actuatorTendencyLabel);
        alpha = createSlider(-5,5, 1);
        alpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setActuatorValue(alpha.getValue());
            }
        });
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
        timeUnitsComboBox = new JComboBox(timeUnits);
        timePanel.add(timeUnitsComboBox);
        contentPane.add(timePanel);
        startButton = new JButton("START");
        startButton.setActionCommand("Start the simulation");
        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActuatorValue(sigma.getValue());
                setActuatorValue(alpha.getValue());
                Main.startSimulation(Integer.parseInt(saladSimulationTimeText.getText()),
                        Integer.parseInt(simulationTimeTextField.getText()));
            }
        });
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
        return slider;
    }

    private void setActuatorValue(double value){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setActuatorStrength(EnvironmentDeviceTypes.TEMPERATURE,value/SLIDER_SCALE);
    }

    private void setSensorValue(double value){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setSensorTendency(EnvironmentDeviceTypes.TEMPERATURE,value/SLIDER_SCALE);
    }

    public JLabel getTemperatureValueLabel() {
        return temperatureValueLabel;
    }

    public JLabel getTimeValueLabel() {
        return timeValueLabel;
    }
}
