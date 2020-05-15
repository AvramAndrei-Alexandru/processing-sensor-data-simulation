package com.andrei;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface ITask5 {
    Map<String, Duration> getActivitiesDuration(List<MonitoredData> dataList);
}
