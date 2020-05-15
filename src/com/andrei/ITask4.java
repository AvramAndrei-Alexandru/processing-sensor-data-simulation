package com.andrei;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ITask4 {
    Map<LocalDateTime, Map<String, Integer>> countActivitiesPerDays(List<MonitoredData> dataList);
}
