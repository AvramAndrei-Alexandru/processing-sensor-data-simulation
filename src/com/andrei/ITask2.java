package com.andrei;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ITask2 {
     Map<LocalDateTime, Integer> countDistinctDays(List<MonitoredData> dataList);
}
