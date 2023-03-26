package com.batchexample.batchdemo.tasks;

import com.batchexample.batchdemo.dto.MerchantAggregateDto;
import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.entity.Merchant;
import com.batchexample.batchdemo.utils.ConstantNumber;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class SampleBatchWithHibernateCursorTasklet implements Tasklet {

  @Autowired SessionFactory sessionFactory;
  private final List<Merchant> merchantList = new ArrayList<>();

  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
      throws Exception {

    /**
     * This is not a correct way to use hibernate cursor
     * its required reader and writer to utilised perfectly.
     */
    HibernateCursorItemReader itemReader = readDataUsingHiberanteCursor();

    itemReader.afterPropertiesSet();

    ExecutionContext executionContext = new ExecutionContext();

    itemReader.open(executionContext);

    Merchant dto = (Merchant) itemReader.read();
    log.info("demo size : {}", dto);


//    while (itemReader != null) {
//      Merchant merchant = (Merchant) itemReader.read();
//      merchantList.add(merchant);
//    }

    itemReader.update(executionContext);

    itemReader.close();

    return RepeatStatus.FINISHED;
  }

  public HibernateCursorItemReader<Merchant> readDataUsingHiberanteCursor() {
    return new HibernateCursorItemReaderBuilder<Merchant>()
        .name("merchantReader")
        .sessionFactory(sessionFactory)
        .queryString("from Merchant")
        .build();
  }
}
