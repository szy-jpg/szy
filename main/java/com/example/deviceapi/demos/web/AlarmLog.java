package com.example.deviceapi.demos.web;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "alarm_log")
public class AlarmLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer deviceId;
    private String alarmType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alarmTime;

    // getter/setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getDeviceId() { return deviceId; }
    public void setDeviceId(Integer deviceId) { this.deviceId = deviceId; }
    public String getAlarmType() { return alarmType; }
    public void setAlarmType(String alarmType) { this.alarmType = alarmType; }
    public Date getAlarmTime() { return alarmTime; }
    public void setAlarmTime(Date alarmTime) { this.alarmTime = alarmTime; }
}
