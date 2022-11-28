package com.ucc.ControlSystem.Utils;

import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentSimulator;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class TimeConvertor {

    private static final long ONE_DAY_IN_SECONDS = 86400;

    public static double convertRealLifeTimeToSaladTime(double realLifeTime){
        return (realLifeTime * EnvironmentSimulator.getEnvironmentSimulator().getDurationOfTheSimulationSaladTime())
                / EnvironmentSimulator.getEnvironmentSimulator().getDurationOfTheSimulationRealLifeTime();
    }

    public static long elapsedTimeInMillis(Timestamp startTime, Timestamp endTime){
        return endTime.getTime() - startTime.getTime();
    }

    public static long convertMillisToSeconds(long millis){
        return TimeUnit.MILLISECONDS.toSeconds(millis);
    }

    public static long getDayInSeconds(int day){
        return day * ONE_DAY_IN_SECONDS;
    }
}
