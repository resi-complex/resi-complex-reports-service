package com.resicomplex.reports.dto;

import java.math.BigDecimal;
import java.util.List;

public record FeeStatsResponse(
        int unitsWithPendingFees,
        int unitsWithCriticalOverdue,
        BigDecimal totalPendingPortfolio,
        List<UnitDebtSummary> criticalOverdueUnits) {
}
