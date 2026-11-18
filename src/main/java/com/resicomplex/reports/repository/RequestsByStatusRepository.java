package com.resicomplex.reports.repository;

import com.resicomplex.reports.model.RequestsByStatusView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsByStatusRepository extends JpaRepository<RequestsByStatusView, String> {
}
