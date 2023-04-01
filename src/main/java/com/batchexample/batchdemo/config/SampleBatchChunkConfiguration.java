package com.batchexample.batchdemo.config;

import com.batchexample.batchdemo.chunktask.SampleTextProcessor;
import com.batchexample.batchdemo.chunktask.SampleTextReader;
import com.batchexample.batchdemo.chunktask.SampleTextWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
@Log4j2
public class SampleBatchChunkConfiguration {
  @Autowired JobBuilderFactory jobBuilderFactory;
  @Autowired StepBuilderFactory stepBuilderFactory;

  @Bean
  Step processChunkSampleStep() {
    return stepBuilderFactory
        .get("SAMPLESTEP")
        .<String, String>chunk(2)
        .reader(new SampleTextReader())
        .processor(new SampleTextProcessor())
        .writer(new SampleTextWriter())
        .build();
  }

  @Bean
  Job processChunkSampleJob() {
    return jobBuilderFactory
        .get("SAMPLEJOB")
        .incrementer(new RunIdIncrementer())
        .flow(processChunkSampleStep())
        .end()
        .build();
  }
}
