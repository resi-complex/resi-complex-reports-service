package com.resicomplex.reports.controller;

import com.resicomplex.reports.dto.FeeStatsResponse;
import com.resicomplex.reports.dto.MaintenanceStatsResponse;
import com.resicomplex.reports.dto.UpdateMaintenanceStatusCountRequest;
import com.resicomplex.reports.dto.UpsertUnitDebtRequest;
import com.resicomplex.reports.service.IReportsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportsController {

    private final IReportsService service;

    public ReportsController(IReportsService service) {
        this.service = service;
    }

    @GetMapping("/fee-stats")
    public FeeStatsResponse getFeeStats() {
        return service.getFeeStats();
    }

    @PostMapping("/fee-stats")
    public FeeStatsResponse upsertFeeProjection(@Valid @RequestBody UpsertUnitDebtRequest request) {
        return service.upsertUnitDebtProjection(request);
    }

    @GetMapping("/maintenance-stats")
    public MaintenanceStatsResponse getMaintenanceStats() {
        return service.getMaintenanceStats();
    }

    @PostMapping("/maintenance-stats")
    public MaintenanceStatsResponse updateMaintenanceProjection(
            @Valid @RequestBody UpdateMaintenanceStatusCountRequest request) {
        return service.updateMaintenanceStatusCount(request);
    }
}
