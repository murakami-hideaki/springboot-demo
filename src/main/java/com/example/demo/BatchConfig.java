package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    // -----------------------------------------------
    // ② JP1から起動されるジョブ
    // -----------------------------------------------
    @Bean
    public Step helloJP1Step(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              HelloTasklet helloTasklet) {
        return new StepBuilder("helloJP1Step", jobRepository)
                .tasklet(helloTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job helloJP1Job(JobRepository jobRepository, Step helloJP1Step) {
        return new JobBuilder("helloJP1Job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(helloJP1Step)
                .build();
    }

    // -----------------------------------------------
    // ③ @Scheduled から起動されるジョブ
    // -----------------------------------------------
    @Bean
    public Step helloScheduledStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    HelloScheduledTasklet helloScheduledTasklet) {
        return new StepBuilder("helloScheduledStep", jobRepository)
                .tasklet(helloScheduledTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job helloScheduledJob(JobRepository jobRepository, Step helloScheduledStep) {
        return new JobBuilder("helloScheduledJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(helloScheduledStep)
                .build();
    }
}
