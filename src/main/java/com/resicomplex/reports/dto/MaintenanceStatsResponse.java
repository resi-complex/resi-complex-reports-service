package com.resicomplex.reports.dto;

import java.util.Map;

public record MaintenanceStatsResponse(
        int totalRequests,
        int pendingCount,
        int inProgressCount,
        int resolvedCount,
        Map<String, Integer> byStatus) {
}
