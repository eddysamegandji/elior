package com.grace.elior.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denomination;
    @Builder.Default
    private Double price = 0.0;
    private LocalDate expenseDate;
    @ManyToOne
    @JoinColumn(name="accounting_id")
    private Accounting accounting;
}
