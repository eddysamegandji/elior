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
    private String name;
    private int quantity;
    @Builder.Default
    private Double purchasePrice = 0.0;
    @Builder.Default
    private Double sellingPrice = 0.0;
    @Builder.Default
    private Double discount = 0.0;
    @Builder.Default
    private Double benefit = 0.0;
    private LocalDate date;
    private Long accounting;

    @PrePersist
    @PreUpdate
    public void calculateBenefit() {
        this.benefit = this.sellingPrice - (this.purchasePrice + this.discount);
    }
}
