package com.ucc.ControlSystem.GUI;

import com.ucc.ControlSystem.ControlSystem.InputParameters.*;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;

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
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JButton updateButton = new JButton("      UPDATE       ");

    private JTextField growthTime = new JTextField();
    private JTextField minAirTemp = new JTextField();
    private JTextField maxAirTemp = new JTextField();
    private JTextField balancingTempCheck = new JTextField();
    private JTextField balanceTempCheck = new JTextField();
    private JLabel currentTime = new JLabel("", JLabel.CENTER);
    private JLabel currentTemp = new JLabel("", JLabel.CENTER);
    private JLabel actuatorState = new JLabel("Off", JLabel.CENTER);
//    private JLabel currentState = new JLabel("", JLabel.CENTER);

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
        this.setSize(580, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        growthTime.setHorizontalAlignment(JTextField.CENTER);
        minAirTemp.setHorizontalAlignment(JTextField.CENTER);
        maxAirTemp.setHorizontalAlignment(JTextField.CENTER);
        balancingTempCheck.setHorizontalAlignment(JTextField.CENTER);
        balanceTempCheck.setHorizontalAlignment(JTextField.CENTER);

        JLabel titleLabel = new JLabel("Initialize/Update System Parameters", JLabel.CENTER);
        titleLabel.setFont(f1);
        JLabel growthTimeLabel = new JLabel("Growth time:");
        growthTimeLabel.setFont(f2);
        JLabel growthTimeUnit = new JLabel("days", JLabel.CENTER);
        growthTimeUnit.setFont(f2);
        JLabel minAirTempLabel = new JLabel("Min. air temperature:");
        minAirTempLabel.setFont(f2);
        JLabel maxAirTempUnit = new JLabel("\u00B0C", JLabel.CENTER);
        maxAirTempUnit.setFont(f2);
        JLabel maxAirTempLabel = new JLabel("Max. air temperature");
        maxAirTempLabel.setFont(f2);
        JLabel minAirTempUnit = new JLabel("\u00B0C", JLabel.CENTER);
        minAirTempUnit.setFont(f2);
        JLabel balanceTempCheckLabel = new JLabel("Temperature check when actuator off:");
        balanceTempCheckLabel.setFont(f2);
        JLabel balanceTempCheckUnit = new JLabel("mins", JLabel.CENTER);
        balanceTempCheckUnit.setFont(f2);
        JLabel balancingTempCheckLabel = new JLabel("Temperature check when actuator on:");
        balancingTempCheckLabel.setFont(f2);
        JLabel balancingTempCheckUnit = new JLabel("mins", JLabel.CENTER);
        balancingTempCheckUnit.setFont(f2);

        card1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        //c.weightx = 1;
        c.weighty = 1;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.CENTER;
        card1.add(titleLabel, c);

        parameters.setLayout(new GridBagLayout());
        GridBagConstraints cc = new GridBagConstraints();
        cc.fill = GridBagConstraints.HORIZONTAL;

        cc.gridx = 0;
        cc.gridy = 0;
        parameters.add(growthTimeLabel, cc);
        cc.gridx = 1;
        cc.gridy = 0;
        cc.ipadx = 80;
        //cc.gridwidth = 3;
        parameters.add(growthTime, cc);
        cc.gridx = 2;
        cc.gridy = 0;
        parameters.add(growthTimeUnit, cc);
        cc.gridx = 0;
        cc.gridy = 1;
        parameters.add(minAirTempLabel, cc);
        cc.gridx = 1;
        cc.gridy = 1;
        cc.ipadx = 80;
        parameters.add(minAirTemp, cc);
        cc.gridx = 2;
        cc.gridy = 1;
        parameters.add(minAirTempUnit, cc);
        cc.gridx = 0;
        cc.gridy = 2;
        parameters.add(maxAirTempLabel, cc);
        cc.gridx = 1;
        cc.gridy = 2;
        cc.ipadx = 80;
        parameters.add(maxAirTemp, cc);
        cc.gridx = 2;
        cc.gridy = 2;
        parameters.add(maxAirTempUnit, cc);
        cc.gridx = 0;
        cc.gridy = 3;
        parameters.add(balanceTempCheckLabel, cc);
        cc.gridx = 1;
        cc.gridy = 3;
        cc.ipadx = 80;
        parameters.add(balanceTempCheck, cc);
        cc.gridx = 2;
        cc.gridy = 3;
        parameters.add(balanceTempCheckUnit, cc);
        cc.gridx = 0;
        cc.gridy = 4;
        parameters.add(balancingTempCheckLabel, cc);
        cc.gridx = 1;
        cc.gridy = 4;
        cc.ipadx = 80;
        parameters.add(balancingTempCheck, cc);
        cc.gridx = 2;
        cc.gridy = 4;
        parameters.add(balancingTempCheckUnit, cc);
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
                            if (getBalanceTempCheck() == 0) {
                                JOptionPane.showMessageDialog(null, "Introduce a correct balanced state temperature checking interval value!!");
                            } else {
                                if (getBalancingTempCheck() == 0) {
                                    JOptionPane.showMessageDialog(null, "Introduce a correct balancing state temperature checking interval value!!");
                                } else{
                                    InputParameterProcessor.getInputParameterProcessor().updateEnvironmentPropertyParameter(getMinAirTemp(), getMaxAirTemp(), EnvironmentDeviceTypes.AIR_TEMPERATURE);
                                    InputParameterProcessor.getInputParameterProcessor().updateMeasurementIntervalParameter(getBalanceTempCheck(), getBalancingTempCheck(), EnvironmentDeviceTypes.AIR_TEMPERATURE);
                                    InputParameterProcessor.getInputParameterProcessor().updateOtherParameter(OtherParameters.GROWTH_TIME, getGrowthTime());
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
        JPanel currentValuesPanel = new JPanel();
        currentValuesPanel.setLayout(new GridLayout(4, 2));
        JLabel currentTimeLabel = new JLabel("Current time in growing phase:");
        currentTimeLabel.setFont(f1);
        currentValuesPanel.add(currentTimeLabel);
        currentTime.setFont(f1);
        currentValuesPanel.add(currentTime);
        JLabel currentTempLabel = new JLabel("Current Temperature:");
        currentTempLabel.setFont(f1);
        currentValuesPanel.add(currentTempLabel);
        currentTemp.setFont(f1);
        currentValuesPanel.add(currentTemp);
        JLabel actuatorStateLabel = new JLabel("Actuator state:");
        actuatorStateLabel.setFont(f1);
        currentValuesPanel.add(actuatorStateLabel);
        actuatorState.setFont(f1);
        currentValuesPanel.add(actuatorState);
//        JLabel currentStateLabel = new JLabel("Current state of the system:");
//        currentStateLabel.setFont(f1);
//        currentValuesPanel.add(currentStateLabel);
//        currentState.setFont(f1);
//        currentValuesPanel.add(currentState);
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
//        c2.gridy = 0;
        c2.gridx = 0;
//        card2.add(new JLabel(""),c2);
        c2.gridy = 3;
        card2.add(startButtonPanel,c2);

        tabbedPane.addTab(INITIALIZEPANEL, card1);
        tabbedPane.addTab(MONITORPANEL, card2);
        tabbedPane.addTab(ALERTSPANEL, card3);
        tabbedPane.addTab(REPORTSPANEL, card4);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.setContentPane(this.tabbedPane);
    }

    public void populateFields(List<InputParameter> inputParameterList){
        for(InputParameter parameter : inputParameterList){
            if(parameter instanceof EnvironmentPropertyParameter){
                EnvironmentPropertyParameter envParam = (EnvironmentPropertyParameter) parameter;
                if(envParam.getType() == EnvironmentDeviceTypes.AIR_TEMPERATURE){
                    minAirTemp.setText(envParam.getMin()+"");
                    maxAirTemp.setText(envParam.getMax()+"");
                }
            }else if(parameter instanceof MeasurementIntervalParameter){
                MeasurementIntervalParameter mintParam = (MeasurementIntervalParameter) parameter;
                if(mintParam.getType() == EnvironmentDeviceTypes.AIR_TEMPERATURE){
                    balanceTempCheck.setText(mintParam.getIntervalBalancedState() / 60 +"");
                    balancingTempCheck.setText(mintParam.getIntervalBalancingState() / 60 +"");
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

    public int getBalancingTempCheck() {
        try{
            return Integer.parseInt(balancingTempCheck.getText()) * 60;
        }catch(NumberFormatException | NullPointerException n){
            return 0;
        }
    }

    public int getBalanceTempCheck() {
        try{
            return Integer.parseInt(balanceTempCheck.getText()) * 60;
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
}
