package com.dev.community.springbatchdemo.batch.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class Job1ExecutionListener implements JobExecutionListener {
    Logger logger = LoggerFactory.getLogger(Job1ExecutionListener.class);
    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Before job...");
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("After job...");
    }
}
