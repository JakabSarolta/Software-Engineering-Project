package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.ControlSystem.JDBC.HSQLQueries;
import com.ucc.ControlSystem.ControlSystem.Reporting.Measurement;
import com.ucc.ControlSystem.GUI.AdminControlPanel;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.GUI.Alert;
import com.ucc.ControlSystem.GUI.EnvironmentControlPanel;
import com.ucc.ControlSystem.Utils.DisplayAdapterInterface;
import com.ucc.ControlSystem.Utils.GUIDisplayAdapterInterfaceImpl;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that controls the EnvironmentController.
 */
public class Controller{
    private static Controller controller = null;

    private DisplayAdapterInterface displayAdapter;

    private States currentState;
    private final Sentinel sentinel;
    private final EnvironmentBalancer environmentBalancer;

    private List<EnvironmentDeviceTypes> parametersToBeBalanced;

    private List<Measurement> measurementList;

    private static final int MEASUREMENT_PERSIST_TIME_INTERVAL = 3600;

    public Controller(DisplayAdapterInterface displayAdapterInterface){
        currentState = States.BALANCED;
        parametersToBeBalanced = new ArrayList<>();
        sentinel = Sentinel.getSentinel();
        environmentBalancer = EnvironmentBalancer.getEnvironmentBalancer(displayAdapterInterface);
        HSQLQueries.getHSQLQueries().emptyTable(Measurement.class);
        measurementList = new ArrayList<>();
        displayAdapter = displayAdapterInterface;
    }

    public void setDisplayAdapter(DisplayAdapterInterface displayAdapter) {
        this.displayAdapter = displayAdapter;
    }

    /**
     * Called when a second passed. If the system is not in a special state, and if the crops are not
     * ready to be harvested yet, it performs check for parameters in the balanced state and for
     * devices in the balancing state.
     * @param currentTime the current time in seconds (salad lifetime)
     */
    public void timePassed(long currentTime){
        if(currentState != States.GROWTH_ENDED  && currentState != States.ALERTED) {
            if (sentinel.isGrowthTimeDue(currentTime)) {
                currentState = States.GROWTH_ENDED;
            } else {
                sentinel.checkPeriodically(currentTime,parametersToBeBalanced,measurementList);
            }

            environmentBalancer.balanceEnvironment(currentTime,parametersToBeBalanced,measurementList);
        }else if(currentState == States.ALERTED){
            currentState = States.BALANCED;
        }else{
            com.ucc.ControlSystem.EnvironmentSimulator.Controller.stopSimulation();
        }

        if(currentTime % MEASUREMENT_PERSIST_TIME_INTERVAL == 0){
            persistMeasurements(measurementList);
        }
    }

    private void persistMeasurements(List<Measurement> measurementList) {
        measurementList.forEach(Measurement::saveMeasurement);
        measurementList.removeAll(measurementList);
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public DisplayAdapterInterface getDisplayAdapter() {
        return displayAdapter;
    }
}
