package com.project.job.configuration;

import com.project.job.SkipEmptyLinePolicy;
import com.project.job.dto.TransactionsDTO;
import com.project.job.entity.Transactions;
import com.project.job.itemProcessor.TransactionItemProcessor;
import com.project.job.itemWriter.TransactionItemWriter;
import com.project.job.listener.TransactionsJobCompletionListener;
import com.project.job.mapper.TransactionFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionBatchConfiguration {


    private static final String FIRST_STEP = "step-1";

    private static final String JOB_NAME = "transactionReadFileJob";

    @Bean
    public ItemReader<TransactionsDTO> transactionItemReader() {
        FlatFileItemReader<TransactionsDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data/dataSource.txt"));
        reader.setLinesToSkip(1); // Skip the header line
        reader.setRecordSeparatorPolicy(new SkipEmptyLinePolicy());
        reader.setLineMapper(new DefaultLineMapper<TransactionsDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("accountNumber","transactionAmount","description","transactionDate","transactionTime","customerId");
                setDelimiter("|");
                setStrict(false);
            }});
            setFieldSetMapper(new TransactionFieldSetMapper());
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<TransactionsDTO, Transactions> transactionItemProcessor() {
        return new TransactionItemProcessor();
    }

    @Bean
    public TransactionItemWriter transactionItemWriter() {
        return new TransactionItemWriter();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ItemReader<TransactionsDTO> transactionItemReader, ItemProcessor<TransactionsDTO, Transactions> transactionItemProcessor,
                      TransactionItemWriter transactionItemWriter) {
        return new StepBuilder(FIRST_STEP, jobRepository)
                .<TransactionsDTO, Transactions>chunk(100, transactionManager)
                .reader(transactionItemReader)
                .processor(transactionItemProcessor)
                .writer(transactionItemWriter)
                .build();
    }

    @Bean("transactionReadFileJob")
    public Job transactionReadFileJob(JobRepository jobRepository, Step step1, TransactionsJobCompletionListener listener) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }
}
