package com.ucc.ControlSystem.Utils;

import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentSimulator;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class TimeConvertor {

    public static double convertRealLifeTimeToSaladTime(double realLifeTime){
        return (realLifeTime * EnvironmentSimulator.getEnvironmentSimulator().getDurationOfTheSimulationSaladTime())
                / EnvironmentSimulator.getEnvironmentSimulator().getDurationOfTheSimulationRealLifeTime();
    }

    public static long elapsedTimeInSeconds(Timestamp startTime, Timestamp endTime){
        return TimeUnit.MILLISECONDS.toSeconds(endTime.getTime() - startTime.getTime());
    }
}
