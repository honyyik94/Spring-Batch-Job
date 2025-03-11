package com.project.job.itemWriter;

import com.project.job.entity.Transactions;
import com.project.job.repository.TransactionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class TransactionItemWriter implements ItemWriter<Transactions> {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public void write(Chunk<? extends Transactions> items) {
        try {
            log.info("Writing data for transactions: {}", items.getItems().size());
            transactionsRepository.saveAll(items.getItems());
        }catch (Exception e) {
            log.error("Error while write file : ", e.getMessage());
        }
    }
}
