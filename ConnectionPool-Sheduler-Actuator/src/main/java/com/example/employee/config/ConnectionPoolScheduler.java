package com.example.employee.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConnectionPoolScheduler {

    private final ConnectionPoolMonitor monitor;

    public ConnectionPoolScheduler(ConnectionPoolMonitor monitor) {
        this.monitor = monitor;
    }

    @Scheduled(fixedRate = 10000)
    public void monitorConnectionPool() {
        monitor.printPoolDetails();
    }
}
