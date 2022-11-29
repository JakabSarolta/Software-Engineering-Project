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
    private JLabel timeValueLabel, airTempValueLabel, waterTempValueLabel, humidityValueLabel,
            waterLevelValueLabel, pHLevelValueLabel, ECValueLabel;
    private JPanel startSimulationPanel;
    private JPanel airTempPanel;
    private JTabbedPane tabs;
    private JTextField simulationTimeTextField, saladSimulationTimeText;
    private JSlider airTempSigma, waterTempSigma, humiditySigma, waterLevelSigma, pHSigma, ECSigma;
    private JSlider airTempAlpha, waterTempAlpha, humidityAlpha, waterLevelAlpha, pHAlpha, ECAlpha;
    private JComboBox timeUnitsComboBox, displayTimeUnitComboBox;

    private final double SLIDER_SCALE_TEN = 10.0;
    private final double SLIDER_SCALE_HUNDRED = 100.0;

    private EnvironmentControlPanel(String title){
        super(title);
        //Create the tab container
        tabs = new JTabbedPane();
        //Set tab container position
        this.setSize(775,350);
        startSimulation();
        prepareTab("Air Temperature Controller", "Temperature (\u00B0C)",
                airTempValueLabel, airTempSigma, airTempAlpha,"Sensor Tendency (\u00B0C/sec)",
                -5,5,1, SLIDER_SCALE_TEN,"Actuator Tendency (\u00B0C/sec)",
                "Air Temperature");
        prepareTab("Water Temperature Controller", "Temperature (\u00B0C)",
                waterTempValueLabel, waterTempSigma, waterTempAlpha,"Sensor Tendency (\u00B0C/sec)",
                -5,5,1, SLIDER_SCALE_TEN,"Actuator Tendency (\u00B0C/sec)",
                "Water Temperature");
        prepareTab("Humidity Controller", "Humidity (%)",
                humidityValueLabel, humiditySigma, humidityAlpha,"Sensor Tendency (%/sec)",
                -9,9,1, SLIDER_SCALE_TEN,"Actuator Tendency (%/sec)",
                "Humidity");
        prepareTab("Water Level Controller", "Level (L)",
                waterLevelValueLabel, waterLevelSigma,waterLevelAlpha,"Sensor Tendency (L/sec)",
                -5,5,1, SLIDER_SCALE_TEN,"Actuator Tendency (L/sec)",
                "Water Level");
        prepareTab("pH Controller", "ph Level",
                pHLevelValueLabel, pHSigma,pHSigma,"Sensor Tendency (unit/sec)",
                -5,5,1, SLIDER_SCALE_HUNDRED,"Actuator Tendency (unit/sec)",
                "pH");
        prepareTab("Electrical Conductivity Controller", "EC (S/m)",
                ECValueLabel, ECSigma, ECAlpha,"Sensor Tendency (S/m)/sec",
                -5,5,1, SLIDER_SCALE_HUNDRED,"Actuator Tendency (S/m)/sec",
                "Electrical Conductivity");
    }

    public static EnvironmentControlPanel getEnvironmentControlPanel(){
        if(environmentControlPanel == null){
            environmentControlPanel = new EnvironmentControlPanel("Vertical Farm Control Simulation");
        }
        return environmentControlPanel;
    }

    public void startSimulation(){
        startSimulationPanel = new JPanel();
        startSimulationPanel.setLayout(new GridLayout(2,1));
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(2,3));
        JLabel simulationTimeLabel = new JLabel("Simulation Time: ", JLabel.CENTER);
        timePanel.add(simulationTimeLabel);
        simulationTimeTextField = new JTextField();
        simulationTimeTextField.setFont(simulationTimeTextField.getFont().deriveFont(16f));
        timePanel.add(simulationTimeTextField);
        JLabel seconds1Label = new JLabel("(in seconds)", JLabel.CENTER);
        timePanel.add(seconds1Label);
        JLabel saladSimulationTimeLabel = new JLabel("Salad Simulation Time: ", JLabel.CENTER);
        timePanel.add(saladSimulationTimeLabel);
        saladSimulationTimeText = new JTextField();
        timePanel.add(saladSimulationTimeText);
        timeUnitsComboBox = new JComboBox(TimeUnits.values());
        timePanel.add(timeUnitsComboBox);
        startSimulationPanel.add(timePanel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        JButton startButton = new JButton("START");
        startButton.setActionCommand("Start the simulation");
        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSensorValue(airTempSigma.getValue());
                setActuatorValue(airTempAlpha.getValue());

                int simulationSaladTimeSeconds = Integer.parseInt(saladSimulationTimeText.getText()) *
                        ((TimeUnits)timeUnitsComboBox.getSelectedItem()).getVal();

                Controller.startSimulation(EnvironmentControlPanel.getEnvironmentControlPanel(),simulationSaladTimeSeconds,
                        Integer.parseInt(simulationTimeTextField.getText()));
            }
        });
        buttonPanel.add(startButton);
        JButton restartButton = new JButton("RESTART");
//        restartButton.setActionCommand("Restart the simulation");
//        restartButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setSensorValue(airTempSigma.getValue());
//                setActuatorValue(airTempAlpha.getValue());
//
//                int simulationSaladTimeSeconds = Integer.parseInt(saladSimulationTimeText.getText()) *
//                        ((TimeUnits)timeUnitsComboBox.getSelectedItem()).getVal();
//
//                Controller.stopSimulation();
//                Controller.startSimulation(EnvironmentControlPanel.getEnvironmentControlPanel(),simulationSaladTimeSeconds,
//                        Integer.parseInt(simulationTimeTextField.getText()));
//            }
//        });
        buttonPanel.add(restartButton);
        startSimulationPanel.add(timePanel);
        startSimulationPanel.add(buttonPanel);
        tabs.add("Start the Simulation", startSimulationPanel);
        this.setContentPane(this.tabs);
    }

    public void prepareTab(String mainTitle, String nameAndMeasurement, JLabel measurementValueLabel,
                           JSlider sigma, JSlider alpha,
                           String sensor, int tendencyMin, int tendencyMax, int spacing, double sliderScale,
                           String actuator, String tabTitle){
        JPanel panel = new JPanel(new GridLayout(7, 1));
        JLabel titleLabel = new JLabel(mainTitle, JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(titleLabel);
        JPanel upperPanel = new JPanel(new GridLayout(1, 5));
        JLabel nameAndMeasurementLabel = new JLabel(nameAndMeasurement, JLabel.CENTER);
        upperPanel.add(nameAndMeasurementLabel);
        measurementValueLabel = new JLabel("", JLabel.CENTER);
        upperPanel.add(measurementValueLabel);
        JLabel timeNameLabel = new JLabel("Current time (salad time)", JLabel.CENTER);
        upperPanel.add(timeNameLabel);
        this.timeValueLabel = new JLabel("", JLabel.CENTER);
        upperPanel.add(timeValueLabel);
        displayTimeUnitComboBox = new JComboBox(TimeUnits.values());
        upperPanel.add(displayTimeUnitComboBox);
        panel.add(upperPanel);
        JLabel sensorTendencyLabel = new JLabel(sensor, JLabel.CENTER);
        panel.add(sensorTendencyLabel);
        sigma = createSlider(tendencyMin, tendencyMax, spacing, sliderScale);
        JSlider finalSigma = sigma;
        sigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(finalSigma.getValue());
            }
        });
        panel.add(sigma);
        JLabel actuatorTendencyLabel = new JLabel(actuator, JLabel.CENTER);
        panel.add(actuatorTendencyLabel);
        alpha = createSlider(tendencyMin, tendencyMax, spacing, sliderScale);
        JSlider finalAlpha = alpha;
        alpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setActuatorValue(finalAlpha.getValue());
            }
        });
        panel.add(alpha);
        tabs.add(tabTitle, panel);
    }

    public JSlider createSlider(int min, int max, int spacing, double div){
        JSlider slider = new JSlider(min,max);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        Hashtable<Integer,JLabel> labelTable = new Hashtable<>();
        for(int i = min; i <= max; i++){
            labelTable.put(i, new JLabel(i/div+""));
        }

        labelTable.put(0, new JLabel("0"));
        slider.setLabelTable(labelTable);
        slider.setMajorTickSpacing(spacing);
        return slider;
    }

    private void setActuatorValue(double value){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setActuatorSetStrength(EnvironmentDeviceTypes.AIR_TEMPERATURE,value/SLIDER_SCALE_TEN);
    }

    private void setSensorValue(double value){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setSensorTendency(EnvironmentDeviceTypes.AIR_TEMPERATURE,value/SLIDER_SCALE_TEN);
    }

    public JLabel getAirTemperatureValueLabel() {
        return airTempValueLabel;
    }

    public JLabel getWaterTempValueLabel() {
        return waterTempValueLabel;
    }

    public JLabel getHumidityValueLabel() {
        return humidityValueLabel;
    }

    public JLabel getWaterLevelValueLabel() {
        return waterLevelValueLabel;
    }

    public JLabel getpHLevelValueLabel() {
        return pHLevelValueLabel;
    }

    public JLabel getECValueLabel() {
        return ECValueLabel;
    }

    public JLabel getTimeValueLabel() {
        return timeValueLabel;
    }

    public JComboBox getDisplayTimeUnitComboBox() {
        return displayTimeUnitComboBox;
    }

    public double getActuatorStrength(){
        return this.airTempAlpha.getValue()/SLIDER_SCALE_TEN;
    }
}
