package com.project.job.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class TransactionsDTO {

    private String id;

    private Long accountNumber;

    private BigDecimal transactionAmount;

    private String description;

    private LocalDate transactionDate;

    private LocalTime transactionTime;

    private Long customerId;
}
