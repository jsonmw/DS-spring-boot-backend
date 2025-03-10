package com.debtsolver.DebtSolver.model;

import com.debtsolver.DebtSolver.util.DebtType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "debts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "debt_type", discriminatorType = DiscriminatorType.STRING)
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Unique debt name required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "Debt type required")
    @Enumerated(EnumType.STRING)
    @Column(name = "debt_type", nullable = false, insertable = false, updatable = false)
    private DebtType debtType;

    @NotNull(message = "APR required")
    @DecimalMin(value = "0.00", inclusive = true)  // Enforcing min value
    @DecimalMax(value = "100.00", inclusive = true) // Enforcing max value
    @Column(precision = 5, scale = 2)
    private BigDecimal apr;

    @NotNull(message = "Balance required")
    @DecimalMin(value = "0.00", inclusive = true)  // Enforcing min value
    @Column(scale = 2)
    private BigDecimal balance;

    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserAccount owner;

    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentPlan> paymentPlans;

}
