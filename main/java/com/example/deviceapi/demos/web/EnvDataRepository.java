package com.example.deviceapi.demos.web;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvDataRepository extends JpaRepository<EnvData, Integer> {
    EnvData findTopByDeviceIdOrderByRecordTimeDesc(Integer deviceId);
}