package com.ucc.ControlSystem.Utils;

import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.GUI.AdminControlPanel;
import com.ucc.ControlSystem.GUI.Alert;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class GUIDisplayAdapterInterfaceImpl implements DisplayAdapterInterface{

    private Map<EnvironmentDeviceTypes, List<JLabel>> deviceToLabelMapControlSystem;
    private Map<EnvironmentDeviceTypes, JLabel> deviceToLabelMapEnvironmentSimulator;
    private JFrame frame;

    public GUIDisplayAdapterInterfaceImpl(Map<EnvironmentDeviceTypes, List<JLabel>> deviceToLabelMap,
                                           Map<EnvironmentDeviceTypes, JLabel> deviceToLabelMapEnvironmentSimulator,
                                           JFrame frame){
        this.deviceToLabelMapControlSystem = deviceToLabelMap;
        this.deviceToLabelMapEnvironmentSimulator = deviceToLabelMapEnvironmentSimulator;
        this.frame = frame;
    }

    @Override
    public void displayAlert(String message, String type, int severity) {
        Alert.alert("The simulation period is over.", "Simulation is over",
                severity);
    }

    @Override
    public void displayCurrentTime(long currentTimeInSeconds) {
        AdminControlPanel.getAdminControlPanel().getCurrentTime().setText(TimeConvertor.convertSeconds(currentTimeInSeconds)+"");

        TimeUnits selectedDisplayTimeUnit = (TimeUnits) ((EnvironmentControlPanel)frame).getDisplayTimeUnitComboBox().getSelectedItem();
        ((EnvironmentControlPanel)frame).getTimeValueLabel().
                setText(adjustCurrentTimeToStep(selectedDisplayTimeUnit,currentTimeInSeconds)+"");
    }

    @Override
    public void displayCurrentValues(Map<EnvironmentDeviceTypes, Double> currentSensorValues) {
        for(EnvironmentDeviceTypes device : currentSensorValues.keySet()){
            deviceToLabelMapControlSystem.get(device).get(0).setText(Math.round(currentSensorValues.get(device) * 10) / 10.0+"");
            EnvironmentControlPanel.deviceToLabelMap.get(device).setText(Math.round(currentSensorValues.get(device)*100)/100.0+"");
        }
    }



    private long adjustCurrentTimeToStep(TimeUnits  selectedTimeUnit, long i){
        return Math.round(Math.floor( ((double) i) / selectedTimeUnit.getVal()));
    }
}
