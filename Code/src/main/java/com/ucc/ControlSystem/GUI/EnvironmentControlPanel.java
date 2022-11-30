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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class EnvironmentControlPanel extends JFrame {

    private static EnvironmentControlPanel environmentControlPanel;
    private JLabel timeValueLabel, airTempValueLabel, waterTempValueLabel, humidityValueLabel,
            waterLevelValueLabel, pHLevelValueLabel, ECValueLabel;
    private JPanel startSimulationPanel;
    private JPanel airTempPanel;
    private JTabbedPane tabs;
    private JTextField simulationTimeTextField, saladSimulationTimeText;
    private final JSlider airTempSigma;
    private final JSlider waterTempSigma;
    private final JSlider humiditySigma;
    private final JSlider waterLevelSigma;
    private final JSlider pHSigma;
    private final JSlider ECSigma;
    private final JSlider airTempAlpha, waterTempAlpha, humidityAlpha, waterLevelAlpha, pHAlpha, ECAlpha;
    private JComboBox timeUnitsComboBox, displayTimeUnitComboBox;

    public static Map<EnvironmentDeviceTypes,JLabel> deviceToLabelMap = populateLabelMap();

    private final double SLIDER_SCALE_TEN = 10.0;
    private final double SLIDER_SCALE_HUNDRED = 100.0;

    private EnvironmentControlPanel(String title){
        super(title);
        //Create the tab container
        tabs = new JTabbedPane();
        //Set tab container position
        this.setSize(775,350);
        startSimulation();
        airTempSigma = createSlider(-5, 5, 1, SLIDER_SCALE_TEN);
        airTempAlpha = createSlider(0, 5, 1, SLIDER_SCALE_TEN);
        airTempValueLabel = new JLabel("", JLabel.CENTER);

        airTempSigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(airTempSigma.getValue(),EnvironmentDeviceTypes.AIR_TEMPERATURE,SLIDER_SCALE_TEN);
            }
        });
        airTempAlpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setActuatorValue(airTempAlpha.getValue(),EnvironmentDeviceTypes.AIR_TEMPERATURE,SLIDER_SCALE_TEN);
            }
        });
        prepareTab("Air Temperature Controller", "Temperature (\u00B0C)",
                airTempValueLabel, airTempSigma, airTempAlpha,"Sensor Tendency (\u00B0C/sec)",
                "Actuator Tendency (\u00B0C/sec)",
                "Air Temperature");

        waterTempSigma = createSlider(-5, 5, 1, SLIDER_SCALE_TEN);
        waterTempAlpha = createSlider(0, 5, 1, SLIDER_SCALE_TEN);
        waterTempValueLabel = new JLabel("", JLabel.CENTER);
        waterTempSigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(waterTempSigma.getValue(),EnvironmentDeviceTypes.WATER_TEMPERATURE,SLIDER_SCALE_TEN);
            }
        });
        waterTempAlpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setActuatorValue(waterTempAlpha.getValue(),EnvironmentDeviceTypes.WATER_TEMPERATURE,SLIDER_SCALE_TEN);
            }
        });
        prepareTab("Water Temperature Controller", "Temperature (\u00B0C)",
                waterTempValueLabel, waterTempSigma, waterTempAlpha,"Sensor Tendency (\u00B0C/sec)"
                ,"Actuator Tendency (\u00B0C/sec)",
                "Water Temperature");

        humiditySigma = createSlider(-9,9,1, SLIDER_SCALE_TEN);
        humidityAlpha = createSlider(0,9,1 ,SLIDER_SCALE_TEN);
        humidityValueLabel = new JLabel("", JLabel.CENTER);
        humiditySigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(humiditySigma.getValue(),EnvironmentDeviceTypes.HUMIDITY,SLIDER_SCALE_TEN);
            }
        });
        humidityAlpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setActuatorValue(humidityAlpha.getValue(),EnvironmentDeviceTypes.HUMIDITY,SLIDER_SCALE_TEN);
            }
        });
        prepareTab("Humidity Controller", "Humidity (%)",
                humidityValueLabel, humiditySigma, humidityAlpha,"Sensor Tendency (%/sec)",
                "Actuator Tendency (%/sec)",
                "Humidity");

        pHSigma = createSlider(-5,5,1, SLIDER_SCALE_HUNDRED);
        pHAlpha = createSlider(0,5,1, SLIDER_SCALE_HUNDRED);
        pHLevelValueLabel = new JLabel("", JLabel.CENTER);
        pHSigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(pHSigma.getValue(),EnvironmentDeviceTypes.PH_LEVEL,SLIDER_SCALE_HUNDRED);
            }
        });
        pHAlpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setActuatorValue(pHAlpha.getValue(),EnvironmentDeviceTypes.PH_LEVEL,SLIDER_SCALE_HUNDRED);
            }
        });
        prepareTab("pH Controller", "ph Level",
                pHLevelValueLabel, pHSigma,pHAlpha,"Sensor Tendency (unit/sec)",
                "Actuator Tendency (unit/sec)",
                "pH");

        ECSigma = createSlider(-5,5,1, SLIDER_SCALE_HUNDRED);
        ECAlpha = createSlider(0,5,1, SLIDER_SCALE_HUNDRED);
        ECValueLabel = new JLabel("", JLabel.CENTER);
        ECSigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(ECSigma.getValue(),EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY,SLIDER_SCALE_HUNDRED);
            }
        });
        ECAlpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setActuatorValue(ECAlpha.getValue(),EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY,SLIDER_SCALE_HUNDRED);
            }
        });
        prepareTab("Electrical Conductivity Controller", "EC (S/m)",
                ECValueLabel, ECSigma, ECAlpha,"Sensor Tendency (S/m)/sec",
                 "Actuator Tendency (S/m)/sec","Electrical Conductivity");


        waterLevelSigma = createSlider(-10,10,1, SLIDER_SCALE_TEN);
        waterLevelAlpha = createSlider(0,10,1, SLIDER_SCALE_TEN);
        waterLevelValueLabel = new JLabel("", JLabel.CENTER);
        waterLevelSigma.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSensorValue(waterLevelSigma.getValue(),EnvironmentDeviceTypes.WATER_LEVEL,SLIDER_SCALE_TEN);
            }
        });
        waterLevelAlpha.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setActuatorValue(waterLevelAlpha.getValue(),EnvironmentDeviceTypes.WATER_LEVEL,SLIDER_SCALE_TEN);
            }
        });
        prepareTab("Water Level Controller", "%",
                waterLevelValueLabel, waterLevelSigma, waterLevelAlpha,"Sensor Tendency %/sec",
                "Actuator Tendency %/sec","Water Level");

    }

    public static EnvironmentControlPanel getEnvironmentControlPanel(){
        if(environmentControlPanel == null){
            environmentControlPanel = new EnvironmentControlPanel("Vertical Farm Control Simulation");
        }
        return environmentControlPanel;
    }

    private static Map<EnvironmentDeviceTypes, JLabel> populateLabelMap() {
        Map<EnvironmentDeviceTypes,JLabel> result = new HashMap<>();
        EnvironmentControlPanel cp = EnvironmentControlPanel.getEnvironmentControlPanel();
        result.put(EnvironmentDeviceTypes.AIR_TEMPERATURE,cp.getAirTemperatureValueLabel());
        result.put(EnvironmentDeviceTypes.WATER_TEMPERATURE,cp.getWaterTempValueLabel());
        result.put(EnvironmentDeviceTypes.HUMIDITY,cp.getHumidityValueLabel());
        result.put(EnvironmentDeviceTypes.PH_LEVEL,cp.getpHLevelValueLabel());
        result.put(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY,cp.getECValueLabel());
        result.put(EnvironmentDeviceTypes.WATER_LEVEL,cp.getWaterLevelValueLabel());
        return result;
    }

    public void startSimulation(){
        startSimulationPanel = new JPanel();
        startSimulationPanel.setLayout(new GridLayout(2,1));
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(3,3));
        JLabel timeNameLabel = new JLabel("Current time (salad time)", JLabel.CENTER);
        timePanel.add(timeNameLabel);
        this.timeValueLabel = new JLabel("", JLabel.CENTER);
        timePanel.add(timeValueLabel);
        displayTimeUnitComboBox = new JComboBox(TimeUnits.values());
        timePanel.add(displayTimeUnitComboBox);
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
        saladSimulationTimeText.setFont(saladSimulationTimeText.getFont().deriveFont(16f));
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
                setSensorValue(airTempSigma.getValue(),EnvironmentDeviceTypes.AIR_TEMPERATURE,SLIDER_SCALE_TEN);
                setActuatorValue(airTempAlpha.getValue(),EnvironmentDeviceTypes.AIR_TEMPERATURE,SLIDER_SCALE_TEN);

                setSensorValue(waterTempSigma.getValue(),EnvironmentDeviceTypes.WATER_TEMPERATURE,SLIDER_SCALE_TEN);
                setActuatorValue(waterTempAlpha.getValue(),EnvironmentDeviceTypes.WATER_TEMPERATURE,SLIDER_SCALE_TEN);

                setSensorValue(humiditySigma.getValue(),EnvironmentDeviceTypes.HUMIDITY,SLIDER_SCALE_TEN);
                setActuatorValue(humidityAlpha.getValue(),EnvironmentDeviceTypes.HUMIDITY,SLIDER_SCALE_TEN);

                setSensorValue(pHSigma.getValue(),EnvironmentDeviceTypes.PH_LEVEL,SLIDER_SCALE_HUNDRED);
                setActuatorValue(pHAlpha.getValue(),EnvironmentDeviceTypes.PH_LEVEL,SLIDER_SCALE_HUNDRED);

                setSensorValue(ECSigma.getValue(),EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY,SLIDER_SCALE_HUNDRED);
                setActuatorValue(ECAlpha.getValue(),EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY,SLIDER_SCALE_HUNDRED);

                setSensorValue(waterLevelSigma.getValue(),EnvironmentDeviceTypes.WATER_LEVEL,SLIDER_SCALE_TEN);
                setActuatorValue(waterLevelAlpha.getValue(),EnvironmentDeviceTypes.WATER_LEVEL,SLIDER_SCALE_TEN);

                try{
                    int simulationSaladTimeSeconds = Integer.parseInt(saladSimulationTimeText.getText()) *
                            ((TimeUnits)timeUnitsComboBox.getSelectedItem()).getVal();
                    if (simulationSaladTimeSeconds <= 0 || Integer.parseInt(simulationTimeTextField.getText()) <= 0){
                        throw new NumberFormatException("Negative");
                    }
                    Controller.startSimulation(EnvironmentControlPanel.getEnvironmentControlPanel(),simulationSaladTimeSeconds,
                            Integer.parseInt(simulationTimeTextField.getText()));
                } catch (NumberFormatException nfe){
                    Alert.alert("Invalid simulation time.\nYou should insert positive integers",
                            "Invalid input", JOptionPane.ERROR_MESSAGE);
                }
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
                           String sensor,
                           String actuator, String tabTitle){
        JPanel panel = new JPanel(new GridLayout(7, 1));
        JLabel titleLabel = new JLabel(mainTitle, JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(titleLabel);
        JPanel upperPanel = new JPanel(new GridLayout(1, 2));
        JLabel nameAndMeasurementLabel = new JLabel(nameAndMeasurement, JLabel.CENTER);
        upperPanel.add(nameAndMeasurementLabel);
        upperPanel.add(measurementValueLabel);
        panel.add(upperPanel);
        JLabel sensorTendencyLabel = new JLabel(sensor, JLabel.CENTER);
        panel.add(sensorTendencyLabel);


        panel.add(sigma);
        JLabel actuatorTendencyLabel = new JLabel(actuator, JLabel.CENTER);
        panel.add(actuatorTendencyLabel);

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

    private void setActuatorValue(double value, EnvironmentDeviceTypes type, double scale){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setActuatorSetStrength(type,value/scale);
    }

    private void setSensorValue(double value, EnvironmentDeviceTypes type, double scale){
        EnvironmentSimulator es = EnvironmentSimulator.getEnvironmentSimulator();
        es.setSensorTendency(type,value/scale);
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
