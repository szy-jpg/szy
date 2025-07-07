package com.example.deviceapi.controller;

import com.example.deviceapi.demos.web.EnvData;
import com.example.deviceapi.demos.web.EnvDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DeviceController {

    @GetMapping("/deviceStatus")
    public Map<String, Object> getDeviceStatus() {
        Map<String, Object> data = new HashMap<>();

        // 设备一
        Map<String, Object> device1 = new HashMap<>();
        double dew1 = Math.round(Math.random() * 1000) / 10.0;
        double pd1 = Math.round(Math.random() * 100) / 10.0;
        double sf61 = Math.round(Math.random() * 100) / 10.0;
        double temp1 = Math.round((20 + Math.random() * 30) * 10) / 10.0;
        device1.put("dew", dew1);
        device1.put("pd", pd1);
        device1.put("sf6", sf61);
        device1.put("temp", temp1);

        // 写入数据库（避免重复）
        EnvData last1 = envDataRepository.findTopByDeviceIdOrderByRecordTimeDesc(1);
        if (last1 == null || last1.getTemperature() != temp1) {
            EnvData env1 = new EnvData();
            env1.setDeviceId(1);
            env1.setTemperature(temp1);
            env1.setHumidity(60.0); // 你可以用实际生成的湿度
            env1.setRecordTime(new java.util.Date());
            envDataRepository.save(env1);
        }

        // 设备二
        Map<String, Object> device2 = new HashMap<>();
        double dew2 = Math.round(Math.random() * 1000) / 10.0;
        double pd2 = Math.round(Math.random() * 100) / 10.0;
        double sf62 = Math.round(Math.random() * 100) / 10.0;
        double temp2 = Math.round((20 + Math.random() * 30) * 10) / 10.0;
        device2.put("dew", dew2);
        device2.put("pd", pd2);
        device2.put("sf6", sf62);
        device2.put("temp", temp2);

        // 写入数据库（避免重复）
        EnvData last2 = envDataRepository.findTopByDeviceIdOrderByRecordTimeDesc(2);
        if (last2 == null || last2.getTemperature() != temp2) {
            EnvData env2 = new EnvData();
            env2.setDeviceId(2);
            env2.setTemperature(temp2);
            env2.setHumidity(65.0); // 你可以用实际生成的湿度
            env2.setRecordTime(new java.util.Date());
            envDataRepository.save(env2);
        }

        data.put("device1", device1);
        data.put("device2", device2);
        return data;
    }

    @GetMapping("/envStatus")
    public Map<String, Object> getEnvStatus() {
        Map<String, Object> map = new HashMap<>();
        // 温度 20~40
        map.put("temp", 20 + (int) (Math.random() * 21));
        // 湿度 50~100
        map.put("humi", 50 + (int) (Math.random() * 51));
        // 可燃气体浓度 10000~99999
        map.put("gas", 10000 + (int) (Math.random() * 90000));
        // 预警 true/false 随机
        map.put("warn", Math.random() > 0.5);

        List<Map<String, Object>> alarms = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Map<String, Object> alarm = new HashMap<>();
            alarm.put("id", i);
            alarm.put("time", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            // 报警温度 20~40
            alarm.put("temp", 20 + (int) (Math.random() * 21));
            // 报警湿度 50~100
            alarm.put("humi", 50 + (int) (Math.random() * 51));
            alarms.add(alarm);
        }
        map.put("alarms", alarms);
        return map;

    }

    @GetMapping("/historyData")
    public List<Map<String, Object>> getHistoryData() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", i);
            row.put("device", (i % 2) + 1); // 设备1或2
            row.put("time", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            row.put("temp", 20 + (int)(Math.random() * 21)); // 温度 20~40
            row.put("humi", 50 + (int)(Math.random() * 51)); // 湿度 50~100
            row.put("dew", Math.round(Math.random() * 1000) / 10.0);   // 微水
            row.put("pd", Math.round(Math.random() * 100) / 10.0);     // 局放
            row.put("sf6", Math.round(Math.random() * 100) / 10.0);    // SF6气体
            list.add(row);
        }
        return list;
    }
    @Autowired
    private EnvDataRepository envDataRepository;
}
