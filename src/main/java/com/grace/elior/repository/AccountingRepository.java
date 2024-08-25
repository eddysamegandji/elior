package com.grace.elior.repository;

import com.grace.elior.model.Accounting;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountingRepository extends JpaRepository<Accounting, Long> {

    List<Accounting> findByEventDateBetween(LocalDate startDate, LocalDate endDate);
}
