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

    /**
     * Converts the time from seconds to a legible format.
     * @param seconds the time in seconds
     * @return the legible string
     */
    public static String convertSeconds(long seconds) {
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
