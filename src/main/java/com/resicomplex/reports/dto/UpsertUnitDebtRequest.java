package com.resicomplex.reports.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpsertUnitDebtRequest(
        @NotBlank @Size(max = 36) String unitId,
        @NotNull @DecimalMin("0.00") BigDecimal totalPending,
        @NotNull @Min(0) Integer overdueMonths,
        LocalDate oldestOverdueAt) {
}
