package com.grace.elior.repository;

import com.grace.elior.model.Article;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
