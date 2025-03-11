package com.project.job.listener;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionsJobCompletionListener implements JobExecutionListener {

    public void beforeJob() {
        log.info("Before Job start");
    }

    public void afterJob() {
        log.info("Job finished");
    }
}
