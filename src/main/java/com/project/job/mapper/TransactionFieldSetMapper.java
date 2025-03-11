package com.project.job.mapper;

import com.project.job.dto.TransactionsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Slf4j
public class TransactionFieldSetMapper implements FieldSetMapper<TransactionsDTO> {

    @Override
    public TransactionsDTO mapFieldSet(FieldSet fieldSet) {
        try {
            TransactionsDTO dto = new TransactionsDTO();
            dto.setId(UUID.randomUUID().toString());
            dto.setAccountNumber(fieldSet.readLong("accountNumber"));
            dto.setTransactionAmount(fieldSet.readBigDecimal("transactionAmount"));
            dto.setDescription(fieldSet.readString("description"));
            dto.setTransactionDate(LocalDate.parse(fieldSet.readString("transactionDate")));
            dto.setTransactionTime(LocalTime.parse(fieldSet.readString("transactionTime")));
            dto.setCustomerId(fieldSet.readLong("customerId"));
            return dto;
        } catch (Exception e) {
            log.error("Skipping invalid record: " + fieldSet.toString());
            return null;
        }
    }

}
