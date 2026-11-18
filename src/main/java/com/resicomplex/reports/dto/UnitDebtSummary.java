package com.resicomplex.reports.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UnitDebtSummary(
        String unitId,
        BigDecimal totalPending,
        Integer overdueMonths,
        LocalDate oldestOverdueAt) {
}
