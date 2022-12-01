package com.ucc.ControlSystem.GUI;

import com.ucc.ControlSystem.ControlSystem.InputParameters.*;
import com.ucc.ControlSystem.ControlSystem.Reporting.ReportGenerator;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final JTextField minWaterLevel = new JTextField();
    private final JTextField maxWaterLevel = new JTextField();
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
    private final JTextField balancingWaterLevel = new JTextField();
    private final JTextField balanceWaterLevel = new JTextField();
    private final JLabel empty = new JLabel("-", JLabel.CENTER);
    private final JLabel empty4 = new JLabel("Actuator state:", JLabel.CENTER);
    private final JLabel currentTime = new JLabel("", JLabel.CENTER);
    private final JLabel currentWaterTemp = new JLabel("", JLabel.CENTER);
    private final JLabel currentHumidity = new JLabel("", JLabel.CENTER);
    private final JLabel currentPhLevel = new JLabel("", JLabel.CENTER);
    private final JLabel currentEC = new JLabel("", JLabel.CENTER);
    private final JLabel currentWaterLevel = new JLabel("", JLabel.CENTER);
    private final JLabel currentTemp = new JLabel("", JLabel.CENTER);
    private final JLabel actuatorState = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState2 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState3 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState4 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState5 = new JLabel("Off", JLabel.CENTER);
    private final JLabel actuatorState6 = new JLabel("Off", JLabel.CENTER);

    private final JButton generateReport = new JButton("GENERATE");
    private final JComboBox parametersBox = new JComboBox(EnvironmentDeviceTypes.values());
    private final JTextField startDay = new JTextField();
    private final JTextField endDay = new JTextField();

    Font  f1  = new Font("Our font", Font.BOLD, 16);
    Font  f2  = new Font("Our font", Font.BOLD, 14);

    public static Map<EnvironmentDeviceTypes, List<JLabel>> deviceToLabelMap = populateDeviceAndLabelMap();

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
        this.setSize(980, 500);
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
                if(inputValidator()) {
                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinAirTemp(), getMaxAirTemp(), EnvironmentDeviceTypes.AIR_TEMPERATURE);
                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getbalanceAirTemp(), getbalancingAirTemp(), EnvironmentDeviceTypes.AIR_TEMPERATURE);
                    InputParameterProcessor.getInputParameterProcessor().updateOtherParameter(OtherParameters.GROWTH_TIME, getGrowthTime());

                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinWaterTemp(),getMaxWaterTemp(),EnvironmentDeviceTypes.WATER_TEMPERATURE);
                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinHumidity(),getMaxHumidity(),EnvironmentDeviceTypes.HUMIDITY);
                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinPhLevel(),getMaxPhLevel(),EnvironmentDeviceTypes.PH_LEVEL);
                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinEC(),getMaxEC(),EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY);
                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinWaterLevel(),getMaxWaterLevel(),EnvironmentDeviceTypes.WATER_LEVEL);

                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceWaterTemp(), getBalancingWaterTemp(), EnvironmentDeviceTypes.WATER_TEMPERATURE);
                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceHumidity(), getBalancingHumidity(), EnvironmentDeviceTypes.HUMIDITY);
                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalancePhLevel(), getBalancingPhLevel(), EnvironmentDeviceTypes.PH_LEVEL);
                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceEC(), getBalancingEC(), EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY);
                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceWaterLevel(), getBalancingWaterLevel(), EnvironmentDeviceTypes.WATER_LEVEL);

                    InputParameterProcessor.getInputParameterProcessor().persistParameters();
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

        setReportPanel();

        tabbedPane.addTab(INITIALIZEPANEL, card1);
        tabbedPane.addTab(MONITORPANEL, card2);
        tabbedPane.addTab(REPORTSPANEL, card4);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setContentPane(this.tabbedPane);
    }

    private boolean inputValidator(){
        if (getGrowthTime() == 0){
            Alert.alert("Introduce a correct growing time!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return  false;
        }
        if (getMinAirTemp() == 0 || getMaxAirTemp() == 0 || getbalanceAirTemp() == 0 || getbalancingAirTemp() == 0 || (getMinAirTemp() > getMaxAirTemp())) {
            Alert.alert("Introduce correct values for air temperature!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return  false;
        }
        if(getMinWaterTemp() == 0 || getMaxWaterTemp() == 0 || getBalancingWaterTemp() == 0 || getBalanceWaterTemp() == 0 || (getMinWaterTemp() > getMaxWaterTemp())){
            Alert.alert("Introduce correct values for water temperature!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return  false;
        }
        if (getMinPhLevel() == 0 || getMaxPhLevel() == 0 || getBalancingPhLevel() == 0 || getBalancePhLevel() == 0 || (getMinPhLevel() > getMaxPhLevel())) {
            Alert.alert("Introduce correct values for pH level!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return  false;
        }
        if (getMinEC() == 0 || getMaxEC() == 0 || getBalancingEC() == 0 || getBalanceEC() == 0 || (getMinEC() > getMaxEC())) {
            Alert.alert("Introduce correct values for electrical conductivity!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return  false;
        }
        if (getMinWaterLevel() == 0 || getMaxWaterLevel() == 0 || getBalancingWaterLevel() == 0 || getBalanceWaterLevel() == 0 || getMinWaterLevel() > getMaxWaterLevel()){
            Alert.alert("Introduce correct values for water level!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return  false;
        }
        if (getMinHumidity() == 0 || getMaxHumidity() == 0 || getBalancingHumidity() == 0 || getBalanceHumidity() == 0 || (getMinHumidity() > getMaxHumidity())){
            Alert.alert("Introduce correct values for humidity!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return  false;
        }
        return true;
    }
    private static Map<EnvironmentDeviceTypes, List<JLabel>> populateDeviceAndLabelMap() {
        Map<EnvironmentDeviceTypes, List<JLabel>> result = new HashMap<>();
        AdminControlPanel cp = AdminControlPanel.getAdminControlPanel();

        result.put(EnvironmentDeviceTypes.AIR_TEMPERATURE,List.of(cp.getCurrentTemp(),cp.getActuatorState()));
        result.put(EnvironmentDeviceTypes.WATER_TEMPERATURE,List.of(cp.getCurrentWaterTemp(),cp.getActuatorState2()));
        result.put(EnvironmentDeviceTypes.HUMIDITY,List.of(cp.getCurrentHumidity(),cp.getActuatorState3()));
        result.put(EnvironmentDeviceTypes.PH_LEVEL,List.of(cp.getCurrentPhLevel(),cp.getActuatorState4()));
        result.put(EnvironmentDeviceTypes.ELECTRICAL_CONDUCTIVITY,List.of(cp.getCurrentEC(),cp.getActuatorState5()));
        result.put(EnvironmentDeviceTypes.WATER_LEVEL,List.of(cp.getCurrentWaterLevel(),cp.getActuatorState6()));

        return result;
    }

    public void changeActuatorState(EnvironmentDeviceTypes device, String state) {
        JLabel actuatorStateLabel = AdminControlPanel.deviceToLabelMap.get(device).get(1);
        actuatorStateLabel.setText(state);
        if ("On".equals(state)) {
            actuatorStateLabel.setForeground(new Color(0x0B830B));
        } else {
            actuatorStateLabel.setForeground(new Color(0x142472));
        }
    }
    private void setReportPanel(){
        card4.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel startDayLabel = new JLabel("Start day", JLabel.CENTER);
        startDayLabel.setFont(f2);
        JLabel endDayLabel = new JLabel("Final day", JLabel.CENTER);
        endDayLabel.setFont(f2);

        c.gridx = 3;
        c.gridy = 0;
        card4.add(startDayLabel, c);
        c.gridx = 4;
        card4.add(endDayLabel, c);
        c.gridx = 2;
        c.gridy = 1;
        c.ipady = 50;
        JLabel selectDayLabel = new JLabel("Select the days:", JLabel.CENTER);
        selectDayLabel.setFont(f1);
        card4.add(selectDayLabel, c);
        c.gridx = 3;
        c.ipady = 0;
        c.ipadx = 250;
        startDay.setHorizontalAlignment(JTextField.CENTER);
        card4.add(startDay, c);

        c.gridx = 4;
        c.ipady = 0;
        c.ipadx = 250;
        endDay.setHorizontalAlignment(JTextField.CENTER);
        card4.add(endDay, c);


        c.gridx = 2;
        c.gridy = 3;
        c.ipadx = 50;
        JLabel parameterLabel = new JLabel("Select the parameter:", JLabel.CENTER);
        parameterLabel.setFont(f1);
        card4.add(parameterLabel, c);
        c.gridx = 4;
        c.gridy = 3;
        c.ipady = 0;
        card4.add(parametersBox, c);
        c.gridx = 3;
        c.gridy = 6;
        c.ipady = 20;

        generateReport.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int startDay = Integer.parseInt(getStartDay().getText()+"");
                    int endDay = Integer.parseInt(getEndDay().getText()+"");
                    if(startDay > endDay){
                        Alert.alert("Start day cannot be after the end day!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    } else{
                        if(startDay < 0 || endDay < 0){
                            Alert.alert("A day cannot have negative values!!", "Invalid input", JOptionPane.ERROR_MESSAGE);
                        } else{
                            ReportGenerator.getReportGenerator().generateReport(startDay,endDay,List.of((EnvironmentDeviceTypes) parametersBox.getSelectedItem()));
                        }
                    }
                }catch (NumberFormatException nfe){
                    System.out.printf("Number format exception");
                }
            }
        });

        card4.add(generateReport, c);
    }

    private void setMonitorWindow(){
        currentValuesPanel.setLayout(new GridLayout(7, 4));
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
        currentValuesPanel.add(empty4);
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
        currentValuesPanel.add(new JLabel(""));
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
        JLabel waterLevelLabel = new JLabel("Water level:");
        waterLevelLabel.setFont(f1);
        waterLevelLabel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(waterLevelLabel);
        currentWaterLevel.setFont(f1);
        currentWaterLevel.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(currentWaterLevel);
        currentValuesPanel.add(new JLabel(""));
        actuatorState6.setFont(f1);
        actuatorState6.setBorder(LineBorder.createBlackLineBorder());
        currentValuesPanel.add(actuatorState6);
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
        minWaterLevel.setHorizontalAlignment(JTextField.CENTER);
        maxWaterLevel.setHorizontalAlignment(JTextField.CENTER);
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
        balancingWaterLevel.setHorizontalAlignment(JTextField.CENTER);
        balanceWaterLevel.setHorizontalAlignment(JTextField.CENTER);

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
        JLabel waterLevelLabel = new JLabel("Water level:");
        waterLevelLabel.setFont(f2);
        JLabel minLabel = new JLabel("Min.", JLabel.CENTER);
        minLabel.setFont(f2);
        JLabel maxLabel = new JLabel("Max.", JLabel.CENTER);
        maxLabel.setFont(f2);
        JLabel measurementUnitLabel = new JLabel("Unit");
        measurementUnitLabel.setFont(f2);
        JLabel airTempUnitLabel = new JLabel("[\u00B0C]");
        airTempUnitLabel.setFont(f2);
        JLabel waterTempUnitLabel = new JLabel("[\u00B0C]");
        waterTempUnitLabel.setFont(f2);
        JLabel humidityUnitLabel = new JLabel("%");
        humidityUnitLabel.setFont(f2);
        JLabel phLevelUnitLabel = new JLabel(""); //has no unit - it's a logarithm
        phLevelUnitLabel.setFont(f2);
        JLabel waterLevelUnitLabel = new JLabel("%");
        waterLevelUnitLabel.setFont(f2);
        JLabel ECUnitLabel = new JLabel("S/m");
        ECUnitLabel.setFont(f2);
        JLabel balanceLabel = new JLabel("Actuator off: [mins]", JLabel.CENTER);
        balanceLabel.setFont(f2);
        JLabel balancingLabel = new JLabel("Actuator on: [mins]", JLabel.CENTER);
        balancingLabel.setFont(f2);
        //
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
        parameters.add(measurementUnitLabel, cc);
        cc.gridx = 4;
        cc.ipady = 10;
        parameters.add(balancingLabel, cc);
        cc.gridx = 5;
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
        cc.ipadx = 20;
        parameters.add(airTempUnitLabel, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancingAirTemp, cc);
        cc.gridx = 5;
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
        cc.ipadx = 20;
        parameters.add(waterTempUnitLabel, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancingWaterTemp, cc);
        cc.gridx = 5;
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
        cc.ipadx = 20;
        parameters.add(humidityUnitLabel, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancingHumidity, cc);
        cc.gridx = 5;
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
        cc.ipadx = 20;
        parameters.add(phLevelUnitLabel, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancingPhLevel, cc);
        cc.gridx = 5;
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
        cc.ipadx = 20;
        parameters.add(ECUnitLabel, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancingEC, cc);
        cc.gridx = 5;
        cc.ipadx = 50;
        parameters.add(balanceEC, cc);

        cc.gridx = 0;
        cc.gridy = 7;
        parameters.add(waterLevelLabel, cc);
        cc.gridx = 1;
        cc.ipadx = 80;
        parameters.add(minWaterLevel, cc);
        cc.gridx = 2;
        cc.ipadx = 80;
        parameters.add(maxWaterLevel, cc);
        cc.gridx = 3;
        cc.ipadx = 20;
        parameters.add(waterLevelUnitLabel, cc);
        cc.gridx = 4;
        cc.ipadx = 50;
        parameters.add(balancingWaterLevel, cc);
        cc.gridx = 5;
        cc.ipadx = 50;
        parameters.add(balanceWaterLevel, cc);
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
                }else if(envParam.getType() == EnvironmentDeviceTypes.WATER_LEVEL){
                    minWaterLevel.setText(envParam.getMin()+"");
                    maxWaterLevel.setText(envParam.getMax()+"");
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
                }else if(mintParam.getType() == EnvironmentDeviceTypes.WATER_LEVEL){
                    balanceWaterLevel.setText(mintParam.getIntervalBalancedState() / 60 +"");
                    balancingWaterLevel.setText(mintParam.getIntervalBalancingState() / 60 +"");
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

    public Double getMinWaterLevel() {
        try{
            return Double.parseDouble(minWaterLevel.getText());
        }catch(NumberFormatException | NullPointerException n){
            return 0.0;
        }
    }

    public Double getMaxWaterLevel() {
        try{
            return Double.parseDouble(maxWaterLevel.getText());
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

    public int getBalancingWaterLevel() {
        try{
            return Integer.parseInt(balancingWaterLevel.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalanceWaterLevel() {
        try{
            return Integer.parseInt(balanceWaterLevel.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public JLabel getCurrentWaterTemp() {
        return currentWaterTemp;
    }

    public JLabel getCurrentHumidity() {
        return currentHumidity;
    }

    public JLabel getCurrentPhLevel() {
        return currentPhLevel;
    }

    public JLabel getCurrentEC() {
        return currentEC;
    }

    public JLabel getCurrentWaterLevel() {
        return currentWaterLevel;
    }

    public JLabel getActuatorState2() {
        return actuatorState2;
    }

    public JLabel getActuatorState3() {
        return actuatorState3;
    }

    public JLabel getActuatorState4() {
        return actuatorState4;
    }

    public JLabel getActuatorState5() {
        return actuatorState5;
    }

    public JLabel getActuatorState6() {
        return actuatorState6;
    }

    public JTextField getStartDay() {
        return startDay;
    }

    public JTextField getEndDay() {
        return endDay;
    }
}
