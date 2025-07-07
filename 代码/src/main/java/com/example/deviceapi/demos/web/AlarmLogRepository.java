package com.example.deviceapi.demos.web;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmLogRepository extends JpaRepository<AlarmLog, Integer> {
}