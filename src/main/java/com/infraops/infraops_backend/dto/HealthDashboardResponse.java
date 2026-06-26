package com.infraops.infraops_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthDashboardResponse {
    private String application;
    private String database;
    private String mongodb;
    private String serverTime;
    private String uptime;
    private String version;
}
