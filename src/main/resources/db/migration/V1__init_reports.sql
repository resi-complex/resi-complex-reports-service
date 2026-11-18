CREATE TABLE requests_by_status_view (
    status VARCHAR(20) PRIMARY KEY,
    request_count INT NOT NULL DEFAULT 0,
    last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE pending_fees_view (
    unit_id VARCHAR(36) PRIMARY KEY,
    total_pending DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    overdue_months INT NOT NULL DEFAULT 0,
    oldest_overdue_at DATE NULL,
    last_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO requests_by_status_view (status, request_count) VALUES
('PENDING', 0),
('IN_PROGRESS', 0),
('RESOLVED', 0);
