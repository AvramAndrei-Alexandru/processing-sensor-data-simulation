package com.andrei;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

class DataOperations {
    private List<MonitoredData> dataList;

    DataOperations(){
        this.dataList = new ArrayList<>();
    }

    private List<MonitoredData> populateList(String inputFile) {
        ITask1 task1 = e -> {
            List<MonitoredData> dataList1 = new ArrayList<>();
            try(Stream<String> stream = Files.lines(Paths.get(inputFile))) {
                stream.map(line -> line.split("\\t\\t")).forEach(y -> addItemToList(y, dataList1));
            }catch (IOException f) {
                f.printStackTrace();
            }
            return dataList1;
        };
        return task1.populateList(inputFile);
    }

    private  void addItemToList(String[] entry, List<MonitoredData> dataList1) {
        if(entry != null && entry.length == 3 && entry[0] != null && entry[1] != null && entry[2] != null) {
            String[] s = entry[2].split("\\t");
            dataList1.add(new MonitoredData(entry[0], entry[1], s[0]));
        }
    }

    void writeResultForTask1(String inputFile) {
        this.dataList = populateList(inputFile);
        if(dataList != null && dataList.size() != 0) {
            try {
                FileWriter fileWriter = new FileWriter("task_1.txt");
                for(MonitoredData monitoredData : dataList) {
                    fileWriter.write("Entry number " + (dataList.indexOf(monitoredData) + 1) + "\n");
                    fileWriter.write(monitoredData.toString() + "\n");
                }
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<LocalDateTime, Integer> countDistinctDays(List<MonitoredData> dataList) {
        ITask2 task2 = e -> {
            Map<LocalDateTime, Integer> daysMap = new TreeMap<>();
            for(MonitoredData monitoredData : dataList) {
                if(!daysMap.containsKey(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS))) {
                    daysMap.put(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS), 1);
                }
                else {
                    daysMap.replace(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS), daysMap.get(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS)) + 1);
                }


            }
            return daysMap;
        };
        return task2.countDistinctDays(dataList);
    }

    void writeResultForTask2() {
        Map<LocalDateTime, Integer> daysMap = countDistinctDays(dataList);
        try {
            FileWriter fileWriter = new FileWriter("task_2.txt");
            fileWriter.write("The number of distinct days is: " + daysMap.size() + "\n");
            for(LocalDateTime localDateTime : daysMap.keySet()) {
                fileWriter.write("The day " + localDateTime.truncatedTo(ChronoUnit.DAYS) + " appeared "  + daysMap.get(localDateTime) + " times\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Integer> countDistinctActivities(List<MonitoredData> dataList) {
        ITask3 task3 = e -> {
            Map<String, Integer> activityMap = new TreeMap<>();

            for(MonitoredData monitoredData : dataList) {
                if(!activityMap.containsKey(monitoredData.getActivity())) {
                    activityMap.put(monitoredData.getActivity(), 1);
                }
                else {
                    activityMap.replace(monitoredData.getActivity(), activityMap.get(monitoredData.getActivity()) + 1);
                }
            }

            return activityMap;
        };
        return task3.countDistinctActivities(dataList);
    }

    void writeResultForTask3(){
        Map<String, Integer> activityMap = countDistinctActivities(dataList);
        try {
            FileWriter fileWriter = new FileWriter("task_3.txt");
            fileWriter.write("The number of distinct activities is: " + activityMap.size() + "\n");
            for(String activity : activityMap.keySet()) {
                fileWriter.write("Activity -> " + activity + " appeared "  + activityMap.get(activity) + " times\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T, P> boolean containsTheElement(Map<T, P> map, T element) {
        return map.containsKey(element);
    }

    private Map<LocalDateTime, Map<String, Integer>> countActivitiesPerDays(List<MonitoredData> dataList) {
        ITask4 task4 = e -> {
            Map<LocalDateTime, Map<String, Integer>> map = new TreeMap<>();
            for(MonitoredData monitoredData : dataList) {
                if(!containsTheElement(map, monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS))) {
                    map.put(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS), new TreeMap<>());
                    map.get(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS)).put(monitoredData.getActivity(), 1);
                }
                else {
                    if(containsTheElement(map.get(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS)), monitoredData.getActivity())) {
                        int count = map.get(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS)).get(monitoredData.getActivity());
                        count++;
                        map.get(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS)).replace(monitoredData.getActivity(), count);
                    }
                    else {
                        map.get(monitoredData.getStartingDate().truncatedTo(ChronoUnit.DAYS)).put(monitoredData.getActivity(), 1);
                    }
                }
            }
            return map;
        };
        return task4.countActivitiesPerDays(dataList);
    }

    void writeResultForTask4() {
        Map<LocalDateTime, Map<String, Integer>> map = countActivitiesPerDays(dataList);
        try {
            FileWriter fileWriter = new FileWriter("task_4.txt");
            for(LocalDateTime localDateTime : map.keySet()) {
                fileWriter.write("\n\nDay " + localDateTime + " contains the activities: \n");
                for(String activity : map.get(localDateTime).keySet()) {
                    fileWriter.write("Activity -> " + activity + " appeared " + map.get(localDateTime).get(activity) + " times\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Duration> getActivitiesDuration(List<MonitoredData> dataList) {
        ITask5 task5 = e -> {
            Map<String, Duration> map = new TreeMap<>();
            for(MonitoredData monitoredData : dataList) {
                if(!containsTheElement(map, monitoredData.getActivity())) {
                    map.put(monitoredData.getActivity(), monitoredData.getDuration());
                }
                else {
                    map.replace(monitoredData.getActivity(), map.get(monitoredData.getActivity()).plus(monitoredData.getDuration()));
                }
            }
            return map;
        };
        return task5.getActivitiesDuration(dataList);
    }

    void writeResultForTask5() {
        Map<String, Duration> map = getActivitiesDuration(dataList);
        try {
            FileWriter fileWriter = new FileWriter("task_5.txt");
            for(String activity : map.keySet()) {
                fileWriter.write("Activity -> " + activity + " with the total duration of " + transformSecondsToTimeFormat(map.get(activity)) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String transformSecondsToTimeFormat(Duration duration) {
        long totalSeconds = duration.getSeconds();
        long hours = totalSeconds / 3600;
        totalSeconds %= 3600;
        long minutes = totalSeconds / 60;
        totalSeconds %= 60;
        long seconds = totalSeconds;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private List<String> filterActivities(List<MonitoredData> dataList) {
        ITask6 task6 = e -> {
            List<String> activities = new ArrayList<>();
            Map<String, Integer> lessThan5MinutesMap = new TreeMap<>();
            Map<String, Integer> totalActivityCount = countDistinctActivities(dataList);
            for(MonitoredData monitoredData : dataList) {
                if(!containsTheElement(lessThan5MinutesMap, monitoredData.getActivity())) {
                    if(monitoredData.getDuration().getSeconds() < 300) {
                        lessThan5MinutesMap.put(monitoredData.getActivity(), 1);
                    }else {
                        lessThan5MinutesMap.put(monitoredData.getActivity(), 0);
                    }
                }
                else {
                    if(monitoredData.getDuration().getSeconds() < 300)
                        lessThan5MinutesMap.replace(monitoredData.getActivity(), lessThan5MinutesMap.get(monitoredData.getActivity()) + 1);
                }
            }
            for(String activity : lessThan5MinutesMap.keySet()) {
                if((float) lessThan5MinutesMap.get(activity) / (float) totalActivityCount.get(activity) >= 0.9) {
                    activities.add(activity);
                }
            }
            return activities;
        };
        return task6.filterActivities(dataList);
    }

    void writeResultForTask6() {
        List<String> activities = filterActivities(dataList);
        try {
            FileWriter fileWriter = new FileWriter("task_6.txt");
            fileWriter.write("Activities with 90% of duration less than 5 min: \n");
            for(String activity : activities) {
                fileWriter.write("Activity -> " + activity + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
