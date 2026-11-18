package com.resicomplex.reports.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pending_fees_view")
@Getter
@Setter
@NoArgsConstructor
public class PendingFeesView {

    @Id
    @Column(name = "unit_id", length = 36)
    private String unitId;

    @Column(name = "total_pending", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPending;

    @Column(name = "overdue_months", nullable = false)
    private Integer overdueMonths;

    @Column(name = "oldest_overdue_at")
    private LocalDate oldestOverdueAt;

    @Column(name = "last_updated_at", nullable = false)
    private LocalDateTime lastUpdatedAt;
}
