package com.project.job.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    private String id;

    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;

    @Column(name = "TRX_AMOUNT")
    private BigDecimal transactionAmount;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TRX_DATE")
    private LocalDate transactionDate;

    @Column(name = "TRX_TIME")
    private LocalTime transactionTime;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Version  // Enables optimistic locking
    private Integer version;

    @PrePersist
    private void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString(); // Manually assign UUID
        }
    }

}
