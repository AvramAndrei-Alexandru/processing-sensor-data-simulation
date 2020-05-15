package com.andrei;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MonitoredData {
    private String startTime;
    private String endTime;
    private String activity;
    private LocalDateTime startingDate;
    private LocalDateTime endDate;

    public MonitoredData(String startTime, String endTime, String activity) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activity = activity;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        startingDate = LocalDateTime.parse(startTime, dateTimeFormatter);
        endDate = LocalDateTime.parse(endTime, dateTimeFormatter);
    }

    @Override
    public String toString() {
        return "MonitoredData{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", activity='" + activity + '\'' +
                '}';
    }

    LocalDateTime getStartingDate() {
        return startingDate;
    }

    String getActivity() {
        return activity;
    }

    Duration getDuration(){
        return Duration.between(startingDate, endDate);
    }

}
