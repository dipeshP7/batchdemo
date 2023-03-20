package com.batchexample.batchdemo.config;

import com.batchexample.batchdemo.exception.CustomBatchException;
import com.batchexample.batchdemo.tasks.SampleBatchWithEntityManagerTasklet;
import com.batchexample.batchdemo.tasks.SampleBatchWithHibernateCursorTasklet;
import com.batchexample.batchdemo.tasks.SampleBatchWithRepositoryTasklet;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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

  @Autowired SampleBatchWithRepositoryTasklet sampleBatchWithRepositoryTaskletTasklet;

  @Autowired SampleBatchWithEntityManagerTasklet sampleBatchWithEntityManagerTasklet;

  @Autowired SampleBatchWithHibernateCursorTasklet sampleBatchWithHibernateCursorTasklet;

  @Bean
  public Job processJob() throws CustomBatchException {

    return jobBuilderFactory
        .get("SAMPLEJOB")
        .incrementer(new RunIdIncrementer())
        .start(processStep1())
        .next(processStepWithRepository())
        .next(processStepWithEntityManager())
        .next(processStepWithHiberanteCursor())
        .build();
  }

  @Bean
  public Step processStep1() {
    return stepBuilderFactory.get("SAMPLESTEP1").tasklet(tasklet()).build();
  }

  @Bean
  public Step processStepWithRepository() throws CustomBatchException {
    return stepBuilderFactory
        .get("SAMPLESTEP2")
        .tasklet(sampleBatchWithRepositoryTaskletTasklet)
        .build();
  }

  @Bean
  public Step processStepWithEntityManager() throws CustomBatchException {
    return stepBuilderFactory
        .get("SAMPLESTEP3")
        .tasklet(sampleBatchWithEntityManagerTasklet)
        .build();
  }

  @Bean
  public Step processStepWithHiberanteCursor() throws CustomBatchException {
    return stepBuilderFactory
        .get("SAMPLESTEP4")
        .tasklet(sampleBatchWithHibernateCursorTasklet)
        .build();
  }

  /**
   * simple tasklet callled in step 1 in job
   *
   * @return
   */
  @Bean
  public Tasklet tasklet() {
    return (contribution, chunkContext) -> {
      log.info("processing first step");
      return RepeatStatus.FINISHED;
    };
  }
}
