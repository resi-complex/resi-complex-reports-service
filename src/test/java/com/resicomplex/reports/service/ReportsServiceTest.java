package com.resicomplex.reports.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.resicomplex.reports.dto.FeeStatsResponse;
import com.resicomplex.reports.dto.MaintenanceStatsResponse;
import com.resicomplex.reports.model.PendingFeesView;
import com.resicomplex.reports.model.RequestsByStatusView;
import com.resicomplex.reports.repository.PendingFeesRepository;
import com.resicomplex.reports.repository.RequestsByStatusRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportsServiceTest {

    @Mock
    private PendingFeesRepository pendingFeesRepository;

    @Mock
    private RequestsByStatusRepository requestsByStatusRepository;

    private ReportsService service;

    @BeforeEach
    void setUp() {
        service = new ReportsService(pendingFeesRepository, requestsByStatusRepository);
    }

    @Test
    void getFeeStatsAggregatesPortfolioAndDetectsUnitsWithMoreThanTwoOverdueMonths() {
        PendingFeesView u1 = new PendingFeesView();
        u1.setUnitId("unit-101");
        u1.setTotalPending(new BigDecimal("250000.00"));
        u1.setOverdueMonths(1);
        u1.setOldestOverdueAt(LocalDate.now().minusMonths(1));
        u1.setLastUpdatedAt(LocalDateTime.now());

        PendingFeesView u2 = new PendingFeesView();
        u2.setUnitId("unit-202");
        u2.setTotalPending(new BigDecimal("750000.00"));
        u2.setOverdueMonths(3);
        u2.setOldestOverdueAt(LocalDate.now().minusMonths(3));
        u2.setLastUpdatedAt(LocalDateTime.now());

        when(pendingFeesRepository.findAll()).thenReturn(List.of(u1, u2));

        FeeStatsResponse stats = service.getFeeStats();

        assertEquals(2, stats.unitsWithPendingFees());
        assertEquals(1, stats.unitsWithCriticalOverdue());
        assertEquals(new BigDecimal("1000000.00"), stats.totalPendingPortfolio());
        assertEquals("unit-202", stats.criticalOverdueUnits().get(0).unitId());
    }

    @Test
    void getMaintenanceStatsAggregatesCountsByStatus() {
        RequestsByStatusView r1 = new RequestsByStatusView();
        r1.setStatus("PENDING");
        r1.setRequestCount(4);
        r1.setLastUpdatedAt(LocalDateTime.now());

        RequestsByStatusView r2 = new RequestsByStatusView();
        r2.setStatus("RESOLVED");
        r2.setRequestCount(11);
        r2.setLastUpdatedAt(LocalDateTime.now());

        when(requestsByStatusRepository.findAll()).thenReturn(List.of(r1, r2));

        MaintenanceStatsResponse stats = service.getMaintenanceStats();

        assertEquals(15, stats.totalRequests());
        assertEquals(4, stats.pendingCount());
        assertEquals(11, stats.resolvedCount());
    }
}
