package com.project.job.itemProcessor;

import com.project.job.dto.TransactionsDTO;
import com.project.job.entity.Transactions;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

@Slf4j
public class TransactionItemProcessor implements ItemProcessor<TransactionsDTO, Transactions> {

    @Override
    public Transactions process(TransactionsDTO transactionsDTO){
        try {
            if (Objects.nonNull(transactionsDTO)) {
                log.debug("Processing data for TransactionsDTO: {}", transactionsDTO);
                Transactions transaction = new Transactions();
                transaction.setAccountNumber(transactionsDTO.getAccountNumber());
                transaction.setTransactionAmount(transactionsDTO.getTransactionAmount());
                transaction.setDescription(transactionsDTO.getDescription());
                transaction.setTransactionDate(transactionsDTO.getTransactionDate());
                transaction.setTransactionTime(transactionsDTO.getTransactionTime());
                transaction.setCustomerId(transactionsDTO.getCustomerId());
                return transaction;
            } else {
                log.error("TransactionsDTO is null");
                return null;
            }
        }catch (Exception e) {
            log.error("Error while reading file : ",  e.getMessage());
            return null;
        }
    }
}
