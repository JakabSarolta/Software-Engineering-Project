package com.ucc.ControlSystem.ControlSystem.EnvironmentControllers;

import com.ucc.ControlSystem.GUI.AdminControlPanel;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;

import java.util.ArrayList;
import java.util.List;

public class Controller{
    private static Controller controller = null;

    private States currentState;
    private final Sentinel sentinel;
    private final EnvironmentBalancer environmentBalancer;

    private List<EnvironmentDeviceTypes> parametersToBeBalanced;

    private Controller(){
        currentState = States.BALANCED;
        parametersToBeBalanced = new ArrayList<>();
        sentinel = Sentinel.getSentinel();
        environmentBalancer = EnvironmentBalancer.getEnvironmentBalancer();
    }

    public static Controller getController(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }

    public void timePassed(long currentTime){
//        switch (currentState) {
//            case BALANCED:
        if(currentState != States.GROWTH_ENDED  && currentState != States.ALERTED) {

            if (sentinel.isGrowthTimeDue(currentTime)) {
                currentState = States.GROWTH_ENDED;
            } else {
//                    if(currentTime % Long.parseLong(confReader.readEnvironmentVariable(SystemConfigParameters.BALANCED_CHECK_INTERVAL)) == 0){
//                        currentState = States.DATA_COLLECTION;
//                        previousState = States.BALANCED;
//                    }
                 sentinel.checkPeriodically(currentTime,parametersToBeBalanced);
//                    if(parametersToBeBalanced.size() > 0){
//                        currentState = States.BALANCING;
//                    }
            }
//                break;
//            case BALANCING:

            environmentBalancer.balanceEnvironment(currentTime, parametersToBeBalanced);

//                if(parametersToBeBalanced.size() == 0){
//                    currentState = States.BALANCED;
//                }
//                break;
//            case GROWTH_ENDED:
//                break;
//            case ALERTED:
//                break;
//        }

            for(EnvironmentDeviceTypes device : EnvironmentDeviceTypes.values()){
                AdminControlPanel.getAdminControlPanel().getCurrentTime().setText(convertSeconds(currentTime)+"");
                AdminControlPanel.getAdminControlPanel().getCurrentTemp().setText(Math.round(DataCollector.getDataCollector().getSensorValue(device) * 10) / 10.0+"");
                AdminControlPanel.getAdminControlPanel().getActuatorState().setText(DataCollector.getDataCollector().getActuatorStrength(device));
            }
        }
    }

    private String convertSeconds(long seconds) {
        if(seconds >= 86400){
            return seconds/86400 + " days " + (seconds - (seconds / 86400) * 86400) / 3600 + " hours";
        }else if(seconds >= 3600){
            return seconds/3600 + " hours " + (seconds  - (seconds / 3600 ) * 3600)  / 60 + " minutes";
        }else if(seconds >= 60){
            return seconds/60 + " minutes " + (seconds - (seconds / 60) * 60)  + " seconds";
        }else {
            return seconds + " seconds";
        }
    }
}
