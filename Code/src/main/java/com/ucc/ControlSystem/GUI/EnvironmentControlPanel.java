package com.ucc.ControlSystem.GUI;

import com.ucc.ControlSystem.EnvironmentSimulator.Controller;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;
import com.ucc.ControlSystem.Utils.TimeUnits;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

public class EnvironmentControlPanel extends JFrame {

    private static EnvironmentControlPanel environmentControlPanel;

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

    private EnvironmentControlPanel(String title){
        super(title);
        prepareGUI();
    }

    public static EnvironmentControlPanel getEnvironmentControlPanel(){
        if(environmentControlPanel == null){
            environmentControlPanel = new EnvironmentControlPanel("Vertical Farm Control Simulation");
        }
        return environmentControlPanel;
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
//        String timeUnits[] = new String[]{"seconds", "minutes", "hours", "days"};
        displayTimeUnitComboBox = new JComboBox(TimeUnits.values());
        simulationPanel.add(displayTimeUnitComboBox);
        contentPane.add(simulationPanel);
        this.sensorTendencyLabel = new JLabel("Sensor Tendency (\u00B0C/sec)", JLabel.CENTER);
        contentPane.add(sensorTendencyLabel);
        sigma = createSlider(-5,5, 1);
        sigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(sigma.getValue());
            }
        });
        contentPane.add(sigma);
        this.actuatorTendencyLabel = new JLabel("Actuator Tendency (\u00B0C/sec)", JLabel.CENTER);
        contentPane.add(actuatorTendencyLabel);
        alpha = createSlider(-5,5, 1);
        alpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
//                setActuatorValue(alpha.getValue());
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
        timeUnitsComboBox = new JComboBox(TimeUnits.values());
        timePanel.add(timeUnitsComboBox);
        contentPane.add(timePanel);
        startButton = new JButton("START");
        startButton.setActionCommand("Start the simulation");
        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActuatorValue(sigma.getValue());
                setActuatorValue(alpha.getValue());

                int simulationSaladTimeSeconds = Integer.parseInt(saladSimulationTimeText.getText()) *
                        ((TimeUnits)timeUnitsComboBox.getSelectedItem()).getVal();

                Controller.startSimulation(EnvironmentControlPanel.getEnvironmentControlPanel(),simulationSaladTimeSeconds,
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
        Hashtable<Integer,JLabel> labelTable = new Hashtable<>();
        for(int i = min; i <= max; i++){
            labelTable.put(i, new JLabel(i/10.0+""));
        }

        labelTable.put(0, new JLabel("0"));
        slider.setLabelTable(labelTable);
        slider.setMajorTickSpacing(spacing);
        return slider;
    }

    private void setActuatorValue(double value){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setActuatorStrength(EnvironmentDeviceTypes.AIR_TEMPERATURE,value/SLIDER_SCALE);
    }

    private void setSensorValue(double value){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setSensorTendency(EnvironmentDeviceTypes.AIR_TEMPERATURE,value/SLIDER_SCALE);
    }

    public JLabel getTemperatureValueLabel() {
        return temperatureValueLabel;
    }

    public JLabel getTimeValueLabel() {
        return timeValueLabel;
    }

    public JComboBox getDisplayTimeUnitComboBox() {
        return displayTimeUnitComboBox;
    }

    public double getActuatorStrength(){
        return this.alpha.getValue()/SLIDER_SCALE;
    }
}
