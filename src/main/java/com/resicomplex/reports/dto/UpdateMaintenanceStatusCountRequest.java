package com.resicomplex.reports.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateMaintenanceStatusCountRequest(
        @NotBlank @Pattern(regexp = "PENDING|IN_PROGRESS|RESOLVED") String status,
        @NotNull @Min(0) Integer requestCount) {
}
