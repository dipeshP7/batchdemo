package com.batchexample.batchdemo.config;

import com.batchexample.batchdemo.tasks.SampleBatchTasklet;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@EnableBatchProcessing
public class SampleBatchConfiguration {

    @Autowired JobBuilderFactory jobBuilderFactory;

    @Autowired StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job processJob(){
        return jobBuilderFactory.get("SAMPLEJOB").incrementer(new RunIdIncrementer()).start(processStep1()).next(processStep2()).build();
    }

    @Bean
    public Step processStep1(){
        return stepBuilderFactory.get("SAMPLESTEP1").tasklet(tasklet()).build();
    }
    @Bean
    public Step processStep2(){
    return stepBuilderFactory.get("SAMPLESTEP2").tasklet(new SampleBatchTasklet()).build();
    }

    @Bean
    public Tasklet tasklet() {
    return (contribution, chunkContext) -> {
    log.info("processing first step");
      return RepeatStatus.FINISHED;
    };
    }
}
