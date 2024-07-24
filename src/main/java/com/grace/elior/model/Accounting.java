package com.grace.elior.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accounting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventName;
    private LocalDate eventDate;
    private String employeeName;
    @Builder.Default
    private Double flooz = 0.0;
    @Builder.Default
    private Double tmoney = 0.0;
    @Builder.Default
    private Double unpaid = 0.0;
    @OneToMany(mappedBy = "accounting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();
    @OneToMany(mappedBy = "accounting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();
    @Builder.Default
    private Double totalPurcharsePrice = 0.0;
    @Builder.Default
    private Double totalSellingPrice = 0.0;
    @Builder.Default
    private Double eventPercent = 0.0;
    @Builder.Default
    private Double totalBenefit = 0.0;
    @Builder.Default
    private Double realBenefit = 0.0;
    @Builder.Default
    private Double pureExpense = 0.0;
    @Builder.Default
    private Double totalExpense = 0.0;

    @PrePersist
    @PreUpdate
    public void calculate () {
        this.totalPurcharsePrice = Optional.ofNullable(this.articles).orElse(Collections.emptyList()).stream().mapToDouble(Article::getPurchasePrice).sum();
        this.totalSellingPrice = Optional.ofNullable(this.articles).orElse(Collections.emptyList()).stream().mapToDouble(Article::getSellingPrice).sum();
        this.totalBenefit = this.totalSellingPrice - this.totalPurcharsePrice;
        this.pureExpense = Optional.ofNullable(this.expenses).orElse(Collections.emptyList()).stream().mapToDouble(Expense::getPrice).sum();
        this.eventPercent = (this.totalBenefit + this.pureExpense) * 0.4;
        this.totalExpense = this.pureExpense + this.eventPercent;
        this.realBenefit = this.totalBenefit - this.totalExpense;
    }
}
