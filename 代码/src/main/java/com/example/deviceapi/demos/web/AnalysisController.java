package com.example.deviceapi.demos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private AlarmLogRepository alarmLogRepository;

    @Autowired
    private EnvDataRepository envDataRepository;

    // 1. 统计设备报警次数
    @GetMapping("/alarm-count")
    public List<Map<String, Object>> getAlarmCount() {
        List<AlarmLog> logs = alarmLogRepository.findAll();
        Map<Integer, Long> countMap = logs.stream()
                .collect(Collectors.groupingBy(AlarmLog::getDeviceId, Collectors.counting()));
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : countMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("device_id", entry.getKey());
            map.put("count", entry.getValue());
            result.add(map);
        }
        return result;
    }

    // 2. 环境温度趋势
    @GetMapping("/temp-trend")
    public List<Map<String, Object>> getTempTrend(@RequestParam Integer deviceId) {
        List<EnvData> dataList = envDataRepository.findAll().stream()
                .filter(e -> e.getDeviceId().equals(deviceId))
                .sorted(Comparator.comparing(EnvData::getRecordTime))
                .collect(Collectors.toList());
        List<Map<String, Object>> result = new ArrayList<>();
        for (EnvData data : dataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("record_time", data.getRecordTime());
            map.put("temperature", data.getTemperature());
            result.add(map);
        }
        return result;
    }

    // 3. 异常检测（如温度>40℃）
    @GetMapping("/anomaly")
    public List<Map<String, Object>> getAnomaly() {
        List<EnvData> dataList = envDataRepository.findAll().stream()
                .filter(e -> e.getTemperature() != null && e.getTemperature() > 40)
                .collect(Collectors.toList());
        List<Map<String, Object>> result = new ArrayList<>();
        for (EnvData data : dataList) {
            Map<String, Object> map = new HashMap<>();
            map.put("device_id", data.getDeviceId());
            map.put("record_time", data.getRecordTime());
            map.put("temperature", data.getTemperature());
            result.add(map);
        }
        return result;
    }
}