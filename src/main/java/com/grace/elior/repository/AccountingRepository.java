package com.grace.elior.repository;

import com.grace.elior.model.Accounting;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountingRepository extends JpaRepository<Accounting, Long> {

    List<Accounting> findByEventDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(e.eventOrganizerCommission) FROM Accounting e WHERE e.eventDate BETWEEN :startDate AND :endDate")
    Double sumEventOrganizerCommissionWithinPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
