package com.project.job.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class JobRunnerConfig {

    private final JobLauncher jobLauncher;
    private final Job myJob;

    public JobRunnerConfig(JobLauncher jobLauncher, Job myJob) {
        this.jobLauncher = jobLauncher;
        this.myJob = myJob;
    }

    @Bean
    public ApplicationRunner runJobOnStartup() {
        return args -> {
           log.info("Starting batch job on application startup...");
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(myJob, params);
        };
    }
}
