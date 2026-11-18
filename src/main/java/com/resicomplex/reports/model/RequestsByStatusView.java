package com.resicomplex.reports.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "requests_by_status_view")
@Getter
@Setter
@NoArgsConstructor
public class RequestsByStatusView {

    @Id
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "request_count", nullable = false)
    private Integer requestCount;

    @Column(name = "last_updated_at", nullable = false)
    private LocalDateTime lastUpdatedAt;
}
