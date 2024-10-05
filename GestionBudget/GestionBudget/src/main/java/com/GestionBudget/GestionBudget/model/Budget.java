package com.GestionBudget.GestionBudget.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@NoArgsConstructor @Getter @Setter @ToString @AllArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    @Column(name = "remaining_amount", nullable = false)
    private Double remainingAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt; // Date de création

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt; // Date de dernière mise à jour


}
