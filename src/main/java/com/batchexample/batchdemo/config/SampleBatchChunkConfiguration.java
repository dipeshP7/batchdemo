package com.batchexample.batchdemo.config;

import com.batchexample.batchdemo.chunktask.*;
import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.entity.Merchant;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@EnableBatchProcessing
@Configuration
@Log4j2
public class SampleBatchChunkConfiguration {
  @Autowired JobBuilderFactory jobBuilderFactory;
  @Autowired StepBuilderFactory stepBuilderFactory;
  @Autowired SampleDatabaseReader sampleDatabaseReader;
  @Autowired SampleDatabaseWriter sampleDatabaseWriter;
  @Autowired DataSource dataSource;

  private static final String QUERY_GET_ALL_MERCHANT =
      "SELECT merchant_id, phone, provider FROM merchant ";

  /**
   * This is Sample Text Reader example. This Step is used by processChunkSampleJob
   *
   * @return
   */
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

  /**
   * This is Sample Text Reader example. this is job will try read data from text array and process
   * it then it will print the text.
   *
   * @return
   */
  @Bean
  Job processChunkSampleJob() {
    return jobBuilderFactory
        .get("SAMPLEJOB")
        .incrementer(new RunIdIncrementer())
        .flow(processChunkSampleStep())
        .end()
        .build();
  }

  @Bean
  Step processChunkDatabaseSampleStep() {
    return stepBuilderFactory
        .get("READDATASTEP")
        .<Merchant, Merchant>chunk(1)
        .reader(jdbcBeanReader())
        .writer(sampleDatabaseWriter)
        .build();
  }

  @Bean
  Step processChunkDatabaseSampleStepCustomJdbcReader() {
    return stepBuilderFactory
        .get("READDATASTEP")
        .<Merchant, Merchant>chunk(1)
        .reader(sampleDatabaseReader)
        .writer(sampleDatabaseWriter)
        .build();
  }

  @Bean
  Job processChunkDatabaseSampleJob() {
    return jobBuilderFactory
        .get("SAMPLEJOB")
        .incrementer(new RunIdIncrementer())
        .flow(processChunkDatabaseSampleStep())
        .end()
        .build();
  }

  @Bean
  Job processChunkDatabaseCustomSampleJob() {
    return jobBuilderFactory
        .get("SAMPLEJOB")
        .incrementer(new RunIdIncrementer())
        .flow(processChunkDatabaseSampleStepCustomJdbcReader())
        .end()
        .build();
  }

  @Bean
  public JdbcCursorItemReader<Merchant> jdbcBeanReader() {
    JdbcCursorItemReader<Merchant> reader = new JdbcCursorItemReader<>();
    reader.setSql(QUERY_GET_ALL_MERCHANT);
    reader.setDataSource(dataSource);
    reader.setRowMapper(
        new RowMapper<Merchant>() {
          @Override
          public Merchant mapRow(ResultSet rs, int rowNum) throws SQLException {
            Merchant merchant = new Merchant();
            merchant.setMerchantId(rs.getString("merchant_id"));
            merchant.setPhone(rs.getString("phone"));
            merchant.setProvider(rs.getString("provider"));
            return merchant;
          }
        });
    return reader;
  }
}
