package com.resicomplex.reports.service;

import com.resicomplex.reports.dto.FeeStatsResponse;
import com.resicomplex.reports.dto.MaintenanceStatsResponse;
import com.resicomplex.reports.dto.UnitDebtSummary;
import com.resicomplex.reports.dto.UpdateMaintenanceStatusCountRequest;
import com.resicomplex.reports.dto.UpsertUnitDebtRequest;
import com.resicomplex.reports.model.PendingFeesView;
import com.resicomplex.reports.model.RequestsByStatusView;
import com.resicomplex.reports.repository.PendingFeesRepository;
import com.resicomplex.reports.repository.RequestsByStatusRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ReportsService implements IReportsService {

    private final PendingFeesRepository pendingFeesRepository;
    private final RequestsByStatusRepository requestsByStatusRepository;

    public ReportsService(
            PendingFeesRepository pendingFeesRepository,
            RequestsByStatusRepository requestsByStatusRepository) {
        this.pendingFeesRepository = pendingFeesRepository;
        this.requestsByStatusRepository = requestsByStatusRepository;
    }

    @Override
    public FeeStatsResponse getFeeStats() {
        List<PendingFeesView> allPending = pendingFeesRepository.findAll();

        BigDecimal totalPendingPortfolio = allPending.stream()
                .map(PendingFeesView::getTotalPending)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        List<UnitDebtSummary> criticalOverdueUnits = allPending.stream()
                .filter(u -> u.getOverdueMonths() != null && u.getOverdueMonths() > 2)
                .map(u -> new UnitDebtSummary(
                        u.getUnitId(),
                        u.getTotalPending(),
                        u.getOverdueMonths(),
                        u.getOldestOverdueAt()))
                .toList();

        int unitsWithPending = (int) allPending.stream()
                .filter(u -> u.getTotalPending().compareTo(BigDecimal.ZERO) > 0)
                .count();

        return new FeeStatsResponse(
                unitsWithPending,
                criticalOverdueUnits.size(),
                totalPendingPortfolio,
                criticalOverdueUnits);
    }

    @Override
    public FeeStatsResponse upsertUnitDebtProjection(UpsertUnitDebtRequest request) {
        PendingFeesView view = pendingFeesRepository.findById(request.unitId())
                .orElseGet(PendingFeesView::new);
        view.setUnitId(request.unitId());
        view.setTotalPending(request.totalPending().setScale(2, RoundingMode.HALF_UP));
        view.setOverdueMonths(request.overdueMonths());
        view.setOldestOverdueAt(request.oldestOverdueAt());
        view.setLastUpdatedAt(LocalDateTime.now());
        pendingFeesRepository.save(view);
        return getFeeStats();
    }

    @Override
    public MaintenanceStatsResponse getMaintenanceStats() {
        List<RequestsByStatusView> rows = requestsByStatusRepository.findAll();
        Map<String, Integer> byStatus = new LinkedHashMap<>();
        byStatus.put("PENDING", 0);
        byStatus.put("IN_PROGRESS", 0);
        byStatus.put("RESOLVED", 0);

        for (RequestsByStatusView row : rows) {
            byStatus.put(row.getStatus(), row.getRequestCount());
        }

        int pending = byStatus.getOrDefault("PENDING", 0);
        int inProgress = byStatus.getOrDefault("IN_PROGRESS", 0);
        int resolved = byStatus.getOrDefault("RESOLVED", 0);
        int total = byStatus.values().stream().mapToInt(Integer::intValue).sum();

        return new MaintenanceStatsResponse(total, pending, inProgress, resolved, byStatus);
    }

    @Override
    public MaintenanceStatsResponse updateMaintenanceStatusCount(UpdateMaintenanceStatusCountRequest request) {
        RequestsByStatusView view = requestsByStatusRepository.findById(request.status())
                .orElseGet(RequestsByStatusView::new);
        view.setStatus(request.status());
        view.setRequestCount(request.requestCount());
        view.setLastUpdatedAt(LocalDateTime.now());
        requestsByStatusRepository.save(view);
        return getMaintenanceStats();
    }
}
