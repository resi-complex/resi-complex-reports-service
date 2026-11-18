package com.resicomplex.reports.service;

import com.resicomplex.reports.dto.FeeStatsResponse;
import com.resicomplex.reports.dto.MaintenanceStatsResponse;
import com.resicomplex.reports.dto.UpdateMaintenanceStatusCountRequest;
import com.resicomplex.reports.dto.UpsertUnitDebtRequest;

public interface IReportsService {

    FeeStatsResponse getFeeStats();

    FeeStatsResponse upsertUnitDebtProjection(UpsertUnitDebtRequest request);

    MaintenanceStatsResponse getMaintenanceStats();

    MaintenanceStatsResponse updateMaintenanceStatusCount(UpdateMaintenanceStatusCountRequest request);
}
