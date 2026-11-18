package com.resicomplex.reports.repository;

import com.resicomplex.reports.model.PendingFeesView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingFeesRepository extends JpaRepository<PendingFeesView, String> {

    List<PendingFeesView> findByOverdueMonthsGreaterThan(Integer months);
}
