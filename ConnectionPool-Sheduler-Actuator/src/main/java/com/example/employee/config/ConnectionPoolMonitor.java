package com.example.employee.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ConnectionPoolMonitor {

    private final HikariDataSource dataSource;

    public ConnectionPoolMonitor(DataSource dataSource) {
        this.dataSource = (HikariDataSource) dataSource;
    }

    public void printPoolDetails() {
        System.out.println("================================");
        System.out.println("Hikari Pool Name: " + dataSource.getPoolName());
        System.out.println("Maximum Pool Size: " + dataSource.getMaximumPoolSize());
        System.out.println("Minimum Idle: " + dataSource.getMinimumIdle());

        if (dataSource.getHikariPoolMXBean() != null) {
            System.out.println("Active Connections: "
                    + dataSource.getHikariPoolMXBean().getActiveConnections());
            System.out.println("Idle Connections: "
                    + dataSource.getHikariPoolMXBean().getIdleConnections());
            System.out.println("Total Connections: "
                    + dataSource.getHikariPoolMXBean().getTotalConnections());
        }

        System.out.println("================================");
    }
}
