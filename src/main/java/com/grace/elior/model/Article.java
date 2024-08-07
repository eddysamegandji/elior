package com.grace.elior.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    private int quantity;
    @Builder.Default
    private Double totalArticlePurchasePrice = 0.0;
    @Builder.Default
    private Double sellingPrice = 0.0;
    @Builder.Default
    private Double benefit = 0.0;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name="accounting_id")
    private Accounting accounting;

    @PrePersist
    @PreUpdate
    public void calculateBenefit() {
        this.totalArticlePurchasePrice += this.product.getUnitPurchasePrice() * this.quantity;
        this.benefit = this.sellingPrice - this.totalArticlePurchasePrice;
    }
}
