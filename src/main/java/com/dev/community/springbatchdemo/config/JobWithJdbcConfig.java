package com.dev.community.springbatchdemo.config;

import com.dev.community.springbatchdemo.batch.listeners.Job1ExecutionListener;
import com.dev.community.springbatchdemo.batch.processors.UserItemProcessor;
import com.dev.community.springbatchdemo.dal.jdbc.mappers.UserRowMapper;
import com.dev.community.springbatchdemo.dal.jpa.entities.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class JobWithJdbcConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private DataSource dataSource;

    public JobWithJdbcConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                             DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public Job job1() {
        return jobBuilderFactory.get("job1")
                .start(step1Job1())
                .listener(new Job1ExecutionListener())
                .build();
    }

    @Bean
    public Step step1Job1() {
        return stepBuilderFactory.get("step1")
                .<User, User>chunk(10)
                .reader(readerJob1())
                .processor(processorJob1())
                .writer(writerJob1())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<User> readerJob1() {
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT id, first_name, last_name FROM users");
        reader.setRowMapper(new UserRowMapper());
        return reader;
    }

    @Bean
    public ItemProcessor<User, User> processorJob1() {
        return new UserItemProcessor();
    }

    @Bean
    public ItemWriter<User> writerJob1() {
        JdbcBatchItemWriter<User> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("UPDATE users SET last_name = :lastName WHERE id = :id");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }

}
