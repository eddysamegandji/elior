package com.grace.elior.repository;

import com.grace.elior.model.Accounting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountingRepository extends JpaRepository<Accounting, Long> {
}
