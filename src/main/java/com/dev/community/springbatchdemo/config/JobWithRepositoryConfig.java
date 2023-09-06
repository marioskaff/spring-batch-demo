package com.dev.community.springbatchdemo.config;

import com.dev.community.springbatchdemo.batch.processors.UserItemProcessor;
import com.dev.community.springbatchdemo.dal.jpa.entities.User;
import com.dev.community.springbatchdemo.dal.jpa.repositories.UserRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;


@Configuration
/**
 * Configuration for job that uses JPA Repository for database communication
 */
public class JobWithRepositoryConfig {
    private final UserRepository userRepository;

    public JobWithRepositoryConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public Job job2(JobRepository jobRepository, Step step1Job2) {
        return new JobBuilder("job2", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1Job2)
                .end()
                .build();
    }

    @Bean
    public Step step1Job2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(readerJob2())
                .processor(processorJob2())
                .writer(writerJob2())
                .build();
    }

    @Bean
    public RepositoryItemReader<User> readerJob2() {
        RepositoryItemReader<User> reader = new RepositoryItemReader<>();
        reader.setPageSize(10);
        reader.setRepository(userRepository);
        reader.setMethodName("findAll");
        Map<String, Sort.Direction> sort = new HashMap<>();
        sort.put("id", Sort.Direction.ASC);
        reader.setSort(sort);
        return reader;
    }

    @Bean
    public ItemProcessor<User, User> processorJob2() {
        return new UserItemProcessor();
    }

    @Bean
    public RepositoryItemWriter<User> writerJob2() {
        RepositoryItemWriter<User> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }

}
