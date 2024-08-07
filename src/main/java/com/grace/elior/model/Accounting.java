package com.grace.elior.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Builder.Default
    private Double totalPurcharsePrice = 0.0;
    @Builder.Default
    private Double totalSellingPrice = 0.0;
    @Builder.Default
    private Integer organizerPercent = 0;
    @Builder.Default
    private Double eventOrganizerCommission = 0.0;
    @Builder.Default
    private Double totalBenefit = 0.0;
    @Builder.Default
    private Double realBenefit = 0.0;
    @Builder.Default
    private Double pureExpense = 0.0;
    @Builder.Default
    private Double totalExpense = 0.0;
    @OneToMany(mappedBy = "accounting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();
    @OneToMany(mappedBy = "accounting", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void calculate () {
        this.totalPurcharsePrice = this.articles.stream().mapToDouble(Article::getTotalArticlePurchasePrice).sum();
        this.totalSellingPrice = this.articles.stream().mapToDouble(Article::getSellingPrice).sum();
        this.totalBenefit = this.totalSellingPrice - this.totalPurcharsePrice;
        this.pureExpense = this.expenses.stream().mapToDouble(Expense::getPrice).sum();
        this.eventOrganizerCommission = (this.totalBenefit - this.pureExpense) * (organizerPercent / 100.0);
        this.totalExpense = this.pureExpense + this.eventOrganizerCommission;
        this.realBenefit = this.totalBenefit - this.totalExpense;
    }
}
