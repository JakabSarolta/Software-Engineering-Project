package com.ucc.ControlSystem.GUI;

import com.ucc.ControlSystem.ControlSystem.InputParameters.*;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdminControlPanel extends JFrame{

    private static AdminControlPanel adminControlPanel = null;

    final static String INITIALIZEPANEL = "Initialize/Update parameters";
    final static String MONITORPANEL = "Monitor system";
    final static String ALERTSPANEL = "Alerts";
    final static String REPORTSPANEL = "Reports";
    private final JPanel card1 = new JPanel();
    private final JPanel card2 = new JPanel();
    private final JPanel card3 = new JPanel();
    private final JPanel card4 = new JPanel();
    private final JPanel parameters = new JPanel();
    private final JPanel currentValuesPanel = new JPanel();
    //private final JPanel simulationTimesPanel = new JPanel();
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JButton updateButton = new JButton("      UPDATE       ");

    private final JTextField growthTime = new JTextField();
    private final JTextField minAirTemp = new JTextField();
    private final JTextField maxAirTemp = new JTextField();
    private final JTextField minWaterTemp = new JTextField();
    private final JTextField maxWaterTemp = new JTextField();
    private final JTextField minHumidity = new JTextField();
    private final JTextField maxHumidity = new JTextField();
    private final JTextField minPhLevel = new JTextField();
    private final JTextField maxPhLevel = new JTextField();
    private final JTextField minEC = new JTextField();
    private final JTextField maxEC = new JTextField();
    private final JTextField minNitrogen = new JTextField();
    private final JTextField maxNitrogen = new JTextField();
    private final JTextField minPhosphorus = new JTextField();
    private final JTextField maxPhosphorus = new JTextField();
    private final JTextField minPotassium = new JTextField();
    private final JTextField maxPotassium = new JTextField();
    private final JTextField balancingAirTemp = new JTextField();
    private final JTextField balanceAirTemp = new JTextField();
    private final JTextField balancingWaterTemp = new JTextField();
    private final JTextField balanceWaterTemp = new JTextField();
    private final JTextField balancingHumidity = new JTextField();
    private final JTextField balanceHumidity = new JTextField();
    private final JTextField balancingPhLevel = new JTextField();
    private final JTextField balancePhLevel = new JTextField();
    private final JTextField balancingEC = new JTextField();
    private final JTextField balanceEC= new JTextField();
    private final JTextField balancingPhosphorus = new JTextField();
    private final JTextField balancePhosphorus = new JTextField();
    private final JTextField balancingNitrogen = new JTextField();
    private final JTextField balanceNitrogen = new JTextField();
    private final JTextField balancingPotassium = new JTextField();
    private final JTextField balancePotassium = new JTextField();
    private final JLabel empty = new JLabel("-", JLabel.CENTER);
    private final JLabel empty4 = new JLabel("Actuator state:", JLabel.CENTER);
    private final JLabel currentTime = new JLabel("", JLabel.CENTER);
    private final JLabel currentWaterTemp = new JLabel("", JLabel.CENTER);
    private final JLabel currentHumidity = new JLabel("", JLabel.CENTER);
    private final JLabel currentPhLevel = new JLabel("", JLabel.CENTER);
    private final JLabel currentEC = new JLabel("", JLabel.CENTER);
    private final JLabel currentNitrogen = new JLabel("", JLabel.CENTER);
    private final JLabel currentPhosphorus = new JLabel("", JLabel.CENTER);
    private final JLabel currentPotassium = new JLabel("", JLabel.CENTER);
    private final JLabel currentTemp = new JLabel("", JLabel.CENTER);
    private final JLabel actuatorState = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState2 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState3 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState4 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState5 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState6 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState7 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState8 = new JLabel("Off", JLabel.CENTER);
    /*private final JLabel simulationTimeLabel = new JLabel("Simulation time: [sec]");
    private final JLabel saladSimulationTimeLabel = new JLabel("Salad simulation time:");
    private final JTextField simulationTime = new JTextField();
    private final JTextField saladSimulationTime = new JTextField();
    private final JComboBox saladUnit = new JComboBox(TimeUnits.values());*/

    Font  f1  = new Font("Our font", Font.BOLD, 16);
    Font  f2  = new Font("Our font", Font.BOLD, 14);

    private AdminControlPanel(String title){
        super(title);
        prepareGUI();
    }

    public static AdminControlPanel getAdminControlPanel(){
        if(adminControlPanel == null){
            adminControlPanel = new AdminControlPanel("Administrator Control");
        }
        return adminControlPanel;
    }

    public void prepareGUI(){
        this.setSize(910, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Initialize/Update System Parameters", JLabel.CENTER);
        titleLabel.setFont(f1);

        card1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.CENTER;
        card1.add(titleLabel, c);

        setInitializeParametersWindow();

        c.gridx = 0;
        c.gridy = 1;
        card1.add(parameters, c);
        c.gridx = 0;
        c.gridy = 2;
        card1.add(new JLabel(""), c);
        updateButton.setBorder(new LineBorder(Color.BLACK));
        updateButton.setFont(f1);
        updateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getGrowthTime() == 0){
                    JOptionPane.showMessageDialog(null, "Introduce a correct growing time!!");
                } else {
                    if (getMinAirTemp() == 0) {
                        JOptionPane.showMessageDialog(null, "Introduce a correct min temperature value!!");
                    } else {
                        if(getMaxAirTemp() == 0){
                            JOptionPane.showMessageDialog(null, "Introduce a correct max temperature value!!");
                        } else
                            if (getbalanceAirTemp() == 0) {
                                JOptionPane.showMessageDialog(null, "Introduce a correct balanced state temperature checking interval value!!");
                            } else {
                                if (getbalancingAirTemp() == 0) {
                                    JOptionPane.showMessageDialog(null, "Introduce a correct balancing state temperature checking interval value!!");
                                } else{
                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinAirTemp(), getMaxAirTemp(), EnvironmentDeviceTypes.AIR_TEMPERATURE);
                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getbalanceAirTemp(), getbalancingAirTemp(), EnvironmentDeviceTypes.AIR_TEMPERATURE);
                                    InputParameterProcessor.getInputParameterProcessor().updateOtherParameter(OtherParameters.GROWTH_TIME, getGrowthTime());

                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinWaterTemp(),getMaxWaterTemp(),EnvironmentDeviceTypes.WATER_TEMPERATURE);
                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinHumidity(),getMaxHumidity(),EnvironmentDeviceTypes.HUMIDITY);
                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinPhLevel(),getMaxPhLevel(),EnvironmentDeviceTypes.PH_LEVEL);
                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinEC(),getMaxEC(),EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY);
//                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinPhosphorus(),getMaxPhosphorus(),EnvironmentDeviceTypes.WATER_TEMPERATURE);
//                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinPotassium(),getMaxPotassium(),EnvironmentDeviceTypes.WATER_TEMPERATURE);
//                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinNitrogen(),getMaxNitrogen(),EnvironmentDeviceTypes.WATER_TEMPERATURE);

                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceWaterTemp(), getBalancingWaterTemp(), EnvironmentDeviceTypes.WATER_TEMPERATURE);
                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceHumidity(), getBalancingHumidity(), EnvironmentDeviceTypes.HUMIDITY);
                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalancePhLevel(), getBalancingPhLevel(), EnvironmentDeviceTypes.PH_LEVEL);
                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceEC(), getBalancingEC(), EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY);
//                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalancePhosphorus(), getBalancingPhosphorus(), EnvironmentDeviceTypes.AIR_TEMPERATURE);
//                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalancePotassium(), getBalancingPotassium(), EnvironmentDeviceTypes.AIR_TEMPERATURE);
//                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceEC(), getBalancingEC(), EnvironmentDeviceTypes.AIR_TEMPERATURE);



                                    InputParameterProcessor.getInputParameterProcessor().persistParameters();
                                }
                        }
                    }
                }
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

        setMonitorWindow();

        c2.gridx = 0;
        c2.gridy = 2;
        card2.add(currentValuesPanel, c2);
        c2.gridy = 2;

        card2.add(new JLabel(""), c2);

        JPanel startButtonPanel = new JPanel();
        startButtonPanel.setLayout(new GridLayout(1,1));
        JButton startButton = new JButton();
        startButton.setText("Start control system");
        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = EnvironmentControlPanel.getEnvironmentControlPanel();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        startButtonPanel.add(startButton);
        c2.gridx = 0;
        c2.gridy = 3;
        card2.add(startButtonPanel,c2);

        tabbedPane.addTab(INITIALIZEPANEL, card1);
        tabbedPane.addTab(MONITORPANEL, card2);
        tabbedPane.addTab(ALERTSPANEL, card3);
        tabbedPane.addTab(REPORTSPANEL, card4);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setContentPane(this.tabbedPane);
    }

    private void setMonitorWindow(){
        currentValuesPanel.setLayout(new GridLayout(9, 4));
        currentValuesPanel.setBorder(LineBorder.createBlackLineBorder());
        JLabel currentTimeLabel = new JLabel("Current growing time:");
        currentTimeLabel.setFont(f1);
        currentTimeLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentTimeLabel);
        currentTime.setFont(f1);
        currentTime.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentTime);
        currentValuesPanel.add(new JLabel(""));
        empty.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(empty);
        JLabel airTempLabel = new JLabel("Air temperature:");
        airTempLabel.setFont(f1);
        airTempLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(airTempLabel);
        currentTemp.setFont(f1);
        currentTemp.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentTemp);
        currentValuesPanel.add(new JLabel(""));
        actuatorState.setFont(f1);
        actuatorState.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState);
        JLabel waterTempLabel = new JLabel("Water temperature:");
        waterTempLabel.setFont(f1);
        waterTempLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(waterTempLabel);
        currentWaterTemp.setFont(f1);
        currentWaterTemp.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentWaterTemp);
        currentValuesPanel.add(new JLabel(""));
        actuatorState2.setFont(f1);
        actuatorState2.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState2);
        JLabel humidityLabel = new JLabel("Humidity:");
        humidityLabel.setFont(f1);
        humidityLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(humidityLabel);
        currentHumidity.setFont(f1);
        currentHumidity.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentHumidity);
        currentValuesPanel.add(new JLabel(""));
        actuatorState3.setFont(f1);
        actuatorState3.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState3);
        JLabel phLevelLabel = new JLabel("Ph level:");
        phLevelLabel.setFont(f1);
        phLevelLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(phLevelLabel);
        currentPhLevel.setFont(f1);
        currentPhLevel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentPhLevel);;
        currentValuesPanel.add(empty4);
        actuatorState4.setFont(f1);
        actuatorState4.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState4);
        JLabel ECLabel = new JLabel("Electrical conductivity:");
        ECLabel.setFont(f1);
        ECLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(ECLabel);
        currentEC.setFont(f1);
        currentEC.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentEC);
        currentValuesPanel.add(new JLabel(""));
        actuatorState5.setFont(f1);
        actuatorState5.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState5);
        JLabel nitrogenLabel = new JLabel("Nitrogen:");
        nitrogenLabel.setFont(f1);
        nitrogenLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(nitrogenLabel);
        currentNitrogen.setFont(f1);
        currentNitrogen.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentNitrogen);
        currentValuesPanel.add(new JLabel(""));
        actuatorState6.setFont(f1);
        actuatorState6.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState6);
        JLabel phosphorusLabel = new JLabel("Phosphorus:");
        phosphorusLabel.setFont(f1);
        phosphorusLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(phosphorusLabel);
        currentPhosphorus.setFont(f1);
        currentPhosphorus.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentPhosphorus);
        currentValuesPanel.add(new JLabel(""));
        actuatorState7.setFont(f1);
        actuatorState7.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState7);
        JLabel potassiumLabel = new JLabel("Potassium:");
        potassiumLabel.setFont(f1);
        potassiumLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(potassiumLabel);
        currentPotassium.setFont(f1);
        currentPotassium.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentPotassium);
        currentValuesPanel.add(new JLabel(""));
        actuatorState8.setFont(f1);
        actuatorState8.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState8);

        /*simulationTimesPanel.setLayout(new GridLayout(2,3));
        simulationTimesPanel.setFont(f1);
        simulationTimesPanel.add(simulationTimeLabel);
        simulationTimesPanel.add(simulationTime);
        simulationTimesPanel.add(new JLabel(""));
        simulationTimesPanel.add(saladSimulationTimeLabel);
        simulationTimesPanel.add(saladSimulationTime);
        simulationTimesPanel.add(saladUnit);*/
    }
    private void setInitializeParametersWindow(){
        growthTime.setHorizontalAlignment(JTextField.CENTER);
        minAirTemp.setHorizontalAlignment(JTextField.CENTER);
        maxAirTemp.setHorizontalAlignment(JTextField.CENTER);
        minWaterTemp.setHorizontalAlignment(JTextField.CENTER);
        maxWaterTemp.setHorizontalAlignment(JTextField.CENTER);
        minHumidity.setHorizontalAlignment(JTextField.CENTER);
        maxHumidity.setHorizontalAlignment(JTextField.CENTER);
        minPhLevel.setHorizontalAlignment(JTextField.CENTER);
        maxPhLevel.setHorizontalAlignment(JTextField.CENTER);
        minEC.setHorizontalAlignment(JTextField.CENTER);
        maxEC.setHorizontalAlignment(JTextField.CENTER);
        minNitrogen.setHorizontalAlignment(JTextField.CENTER);
        maxNitrogen.setHorizontalAlignment(JTextField.CENTER);
        minPhosphorus.setHorizontalAlignment(JTextField.CENTER);
        maxPhosphorus.setHorizontalAlignment(JTextField.CENTER);
        minPotassium.setHorizontalAlignment(JTextField.CENTER);
        maxPotassium.setHorizontalAlignment(JTextField.CENTER);
        balancingAirTemp.setHorizontalAlignment(JTextField.CENTER);
        balanceAirTemp.setHorizontalAlignment(JTextField.CENTER);
        balancingWaterTemp.setHorizontalAlignment(JTextField.CENTER);
        balanceWaterTemp.setHorizontalAlignment(JTextField.CENTER);
        balancingHumidity.setHorizontalAlignment(JTextField.CENTER);
        balanceHumidity.setHorizontalAlignment(JTextField.CENTER);
        balancingPhLevel.setHorizontalAlignment(JTextField.CENTER);
        balancePhLevel.setHorizontalAlignment(JTextField.CENTER);
        balancingEC.setHorizontalAlignment(JTextField.CENTER);
        balanceEC.setHorizontalAlignment(JTextField.CENTER);
        balancingNitrogen.setHorizontalAlignment(JTextField.CENTER);
        balanceNitrogen.setHorizontalAlignment(JTextField.CENTER);
        balancingPhosphorus.setHorizontalAlignment(JTextField.CENTER);
        balancePhosphorus.setHorizontalAlignment(JTextField.CENTER);
        balancingPotassium.setHorizontalAlignment(JTextField.CENTER);
        balancePotassium.setHorizontalAlignment(JTextField.CENTER);

        JLabel growthTimeLabel = new JLabel("Growth time:");
        growthTimeLabel.setFont(f2);
        JLabel growthTimeUnit = new JLabel("days");
        growthTimeUnit.setFont(f2);
        JLabel airTempLabel = new JLabel("Air temperature:");
        airTempLabel.setFont(f2);
        JLabel waterTempLabel = new JLabel("Water temperature:");
        waterTempLabel.setFont(f2);
        JLabel humidityLabel = new JLabel("Humidity:");
        humidityLabel.setFont(f2);
        JLabel phLevelLabel = new JLabel("Ph Level:");
        phLevelLabel.setFont(f2);
        JLabel ECLabel = new JLabel("Electrical Conductivity:");
        ECLabel.setFont(f2);
        JLabel nitrogenLabel = new JLabel("Nitrogen:");
        nitrogenLabel.setFont(f2);
        JLabel phosphorusLabel = new JLabel("Phosphorus:");
        phosphorusLabel.setFont(f2);
        JLabel potassiumLabel = new JLabel("Potassium:");
        potassiumLabel.setFont(f2);
        JLabel minLabel = new JLabel("Min. [\u00B0C]", JLabel.CENTER);
        minLabel.setFont(f2);
        JLabel maxLabel = new JLabel("Max. [\u00B0C]", JLabel.CENTER);
        maxLabel.setFont(f2);
        JLabel balanceLabel = new JLabel("Actuator off: [mins]", JLabel.CENTER);
        balanceLabel.setFont(f2);
        JLabel balancingLabel = new JLabel("Actuator on: [mins]", JLabel.CENTER);
        balancingLabel.setFont(f2);

        parameters.setLayout(new GridBagLayout());
        GridBagConstraints cc = new GridBagConstraints();
        cc.fill = GridBagConstraints.HORIZONTAL;

        cc.gridx = 0;
        cc.gridy = 0;
        parameters.add(growthTimeLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(growthTime, cc);
        cc.gridx = 2;
        parameters.add(growthTimeUnit, cc);

        cc.gridx = 1;
        cc.gridy = 1;
        cc.ipady = 10;
        parameters.add(minLabel, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        cc.ipady = 10;
        parameters.add(maxLabel, cc);
        cc.gridx = 3;
        cc.ipady = 10;
        parameters.add(balancingLabel, cc);
        cc.gridx = 4;
        cc.ipady = 10;
        parameters.add(balanceLabel, cc);

        cc.gridx = 0;
        cc.gridy = 2;
        parameters.add(airTempLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minAirTemp, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxAirTemp, cc);
        cc.gridx = 3;
        cc.ipadx = 50;
        parameters.add(balancingAirTemp, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balanceAirTemp, cc);

        cc.gridx = 0;
        cc.gridy = 3;
        parameters.add(waterTempLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minWaterTemp, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxWaterTemp, cc);
        cc.gridx = 3;
        cc.ipadx = 50;
        parameters.add(balancingWaterTemp, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balanceWaterTemp, cc);

        cc.gridx = 0;
        cc.gridy = 4;
        parameters.add(humidityLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minHumidity, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxHumidity, cc);
        cc.gridx = 3;
        cc.ipadx = 50;
        parameters.add(balancingHumidity, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balanceHumidity, cc);

        cc.gridx = 0;
        cc.gridy = 5;
        parameters.add(phLevelLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minPhLevel, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxPhLevel, cc);
        cc.gridx = 3;
        cc.ipadx = 50;
        parameters.add(balancingPhLevel, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancePhLevel, cc);

        cc.gridx = 0;
        cc.gridy = 6;
        parameters.add(ECLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minEC, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxEC, cc);
        cc.gridx = 3;
        cc.ipadx = 50;
        parameters.add(balancingEC, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balanceEC, cc);

        cc.gridx = 0;
        cc.gridy = 7;
        parameters.add(nitrogenLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minNitrogen, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxNitrogen, cc);
        cc.gridx = 3;
        cc.ipadx = 50;
        parameters.add(balancingNitrogen, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balanceNitrogen, cc);

        cc.gridx = 0;
        cc.gridy = 8;
        parameters.add(phosphorusLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minPhosphorus, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxPhosphorus, cc);
        cc.gridx = 3;
        cc.ipadx = 50;
        parameters.add(balancingPhosphorus, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancePhosphorus, cc);

        cc.gridx = 0;
        cc.gridy = 9;
        parameters.add(potassiumLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minPotassium, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxPotassium, cc);
        cc.gridx = 3;
        cc.ipadx = 50;
        parameters.add(balancingPotassium, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancePotassium, cc);
    }

    public void populateFields(List<InputParameter> inputParameterList){
        for(InputParameter parameter : inputParameterList){
            if(parameter instanceof EnvironmentPropertyParameter){
                EnvironmentPropertyParameter envParam = (EnvironmentPropertyParameter) parameter;
                if(envParam.getType() == EnvironmentDeviceTypes.AIR_TEMPERATURE){
                    minAirTemp.setText(envParam.getMin()+"");
                    maxAirTemp.setText(envParam.getMax()+"");
                }else if(envParam.getType() == EnvironmentDeviceTypes.WATER_TEMPERATURE){
                    minWaterTemp.setText(envParam.getMin()+"");
                    maxWaterTemp.setText(envParam.getMax()+"");
                }else if(envParam.getType() == EnvironmentDeviceTypes.HUMIDITY){
                    minHumidity.setText(envParam.getMin()+"");
                    maxHumidity.setText(envParam.getMax()+"");
                }else if(envParam.getType() == EnvironmentDeviceTypes.PH_LEVEL){
                    minPhLevel.setText(envParam.getMin()+"");
                    maxPhLevel.setText(envParam.getMax()+"");
                }else if(envParam.getType() == EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY){
                    minEC.setText(envParam.getMin()+"");
                    maxEC.setText(envParam.getMax()+"");
                }
            }else if(parameter instanceof MeasurementIntervalParameter){
                MeasurementIntervalParameter mintParam = (MeasurementIntervalParameter) parameter;
                if(mintParam.getType() == EnvironmentDeviceTypes.AIR_TEMPERATURE){
                    balanceAirTemp.setText(mintParam.getIntervalBalancedState() / 60 +"");
                    balancingAirTemp.setText(mintParam.getIntervalBalancingState() / 60 +"");
                }else if(mintParam.getType() == EnvironmentDeviceTypes.WATER_TEMPERATURE){
                    balanceWaterTemp.setText(mintParam.getIntervalBalancedState() / 60 +"");
                    balancingWaterTemp.setText(mintParam.getIntervalBalancingState() / 60 +"");
                }else if(mintParam.getType() == EnvironmentDeviceTypes.HUMIDITY){
                    balanceHumidity.setText(mintParam.getIntervalBalancedState() / 60 +"");
                    balancingHumidity.setText(mintParam.getIntervalBalancingState() / 60 +"");
                }else if(mintParam.getType() == EnvironmentDeviceTypes.PH_LEVEL){
                    balancePhLevel.setText(mintParam.getIntervalBalancedState() / 60 +"");
                    balancingPhLevel.setText(mintParam.getIntervalBalancingState() / 60 +"");
                }else if(mintParam.getType() == EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY){
                    balanceEC.setText(mintParam.getIntervalBalancedState() / 60 +"");
                    balancingEC.setText(mintParam.getIntervalBalancingState() / 60 +"");
                }
            }else if(parameter instanceof OtherParameter){
                OtherParameter othrParam = (OtherParameter) parameter;
                if(othrParam.getType() == OtherParameters.GROWTH_TIME){
                    growthTime.setText(othrParam.getValue() / 86400 +"");
                }
            }
        }
    }

    public long getGrowthTime() {
        try{
            //convert days into secs
            return Long.parseLong(growthTime.getText()) * 86400;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public double getMinAirTemp() {
        try{
            return Double.parseDouble(minAirTemp.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public double getMaxAirTemp() {
        try{
            return Double.parseDouble(maxAirTemp.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getbalancingAirTemp() {
        try{
            return Integer.parseInt(balancingAirTemp.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getbalanceAirTemp() {
        try{
            return Integer.parseInt(balanceAirTemp.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n) {
            return 0;
        }
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime.setText(String.valueOf(currentTime));
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp.setText(String.valueOf(currentTemp));
    }

    public void setActuatorState(String actuatorState) {
        this.actuatorState.setText(String.valueOf(actuatorState));
    }

//    public void setCurrentState(String currentState) {
//        this.currentState.setText(String.valueOf(currentState));
//    }


    public JLabel getCurrentTime() {
        return currentTime;
    }

    public JLabel getCurrentTemp() {
        return currentTemp;
    }

    public JLabel getActuatorState() {
        return actuatorState;
    }

    public Double getMinWaterTemp() {
        try{
            return Double.parseDouble(minWaterTemp.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMaxWaterTemp() {
        try{
            return Double.parseDouble(maxWaterTemp.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMinHumidity() {
        try{
            return Double.parseDouble(minHumidity.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMaxHumidity() {
        try{
            return Double.parseDouble(maxHumidity.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMinPhLevel() {
        try{
            return Double.parseDouble(minPhLevel.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMaxPhLevel() {
        try{
            return Double.parseDouble(maxPhLevel.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMinEC() {
        try{
            return Double.parseDouble(minEC.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMaxEC() {
        try{
            return Double.parseDouble(maxEC.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMinNitrogen() {
        try{
            return Double.parseDouble(minNitrogen.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMaxNitrogen() {
        try{
            return Double.parseDouble(maxNitrogen.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMinPhosphorus() {
        try{
            return Double.parseDouble(minPhosphorus.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMaxPhosphorus() {
        try{
            return Double.parseDouble(maxPhosphorus.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMinPotassium() {
        try{
            return Double.parseDouble(minPotassium.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMaxPotassium() {
        try{
            return Double.parseDouble(maxPotassium.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public int getBalancingWaterTemp() {
        try{
            return Integer.parseInt(balancingWaterTemp.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalanceWaterTemp() {
        try{
            return Integer.parseInt(balanceWaterTemp.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancingHumidity() {
        try{
            return Integer.parseInt(balancingHumidity.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalanceHumidity() {
        try{
            return Integer.parseInt(balanceHumidity.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancingPhLevel() {
        try{
            return Integer.parseInt(balancingPhLevel.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancePhLevel() {
        try{
            return Integer.parseInt(balancePhLevel.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancingEC() {
        try{
            return Integer.parseInt(balancingEC.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalanceEC() {
        try{
            return Integer.parseInt(balanceEC.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancingPhosphorus() {
        try{
            return Integer.parseInt(balancePhosphorus.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancePhosphorus() {
        try{
            return Integer.parseInt(balancePhosphorus.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancingNitrogen() {
        try{
            return Integer.parseInt(balancingNitrogen.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalanceNitrogen() {
        try{
            return Integer.parseInt(balanceNitrogen.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancingPotassium() {
        try{
            return Integer.parseInt(balancingPotassium.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalancePotassium() {
        try{
            return Integer.parseInt(balancePotassium.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }
}
