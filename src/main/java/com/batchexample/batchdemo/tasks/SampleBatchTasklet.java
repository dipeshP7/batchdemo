package com.batchexample.batchdemo.tasks;

import com.batchexample.batchdemo.dto.MerchantAggregateDto;
import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.exception.CustomBatchException;
import com.batchexample.batchdemo.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.HibernateCursorItemReader;import org.springframework.batch.item.database.builder.HibernateCursorItemReaderBuilder;import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;import javax.persistence.PersistenceContext;import java.util.List;import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class SampleBatchTasklet implements Tasklet {

  @Autowired MerchantRepository merchantRepository;

  @PersistenceContext
  EntityManager entityManager;

  @Autowired SessionFactory sessionFactory;

  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
      throws Exception {
    try {
      log.info("calling in smaple batch tasklet");
      // read all the data
      List<MerchantDto> merchantDtoList = merchantRepository.findAllMerchants();
      log.info("all data : {}", merchantDtoList.size());
      // read aggregate data
      List<MerchantAggregateDto> merchantAggregateDto = merchantRepository.findMerchantByProvider();
      log.info("aggregate data: {}", merchantAggregateDto.size());

      List<MerchantDto> merchantDtoList1 = findMerchantData();
      log.info("mdto1 size : {}", merchantDtoList1.size());

      List<MerchantAggregateDto> merchantAggregateDtoList = findMerchantDataByProvider();
      log.info("mAggdto1 size : {}", merchantAggregateDtoList.size());

//      HibernateCursorItemReader demoMerchantList = readDataUsingCursor();
//       https://www.linkedin.com/pulse/spring-batch-read-from-db-input-prateek-ashtikar
//      log.info("demo size : {}",demoMerchantList.size());

      for(int listSize = 0; listSize < merchantAggregateDto.size(); listSize++){
          MerchantAggregateDto dto = merchantAggregateDto.get(listSize);
          log.info( "{} : {}",dto.getProvider(), dto.getTotal());
      }

      for(int listSize = 0; listSize < merchantAggregateDtoList.size(); listSize++){
        MerchantAggregateDto dto = merchantAggregateDtoList.get(listSize);
        log.info( " {} :  {}",dto.getProvider(), dto.getTotal());
      }
    } catch (CustomBatchException | NullPointerException e) {
      log.info("unable to process the batch error : {}", e.getMessage());
      throw new CustomBatchException(e.getMessage());
    }
    return RepeatStatus.FINISHED;
  }

  public List<MerchantDto> findMerchantData() {
    return entityManager
            .createQuery("select new com.batchexample.batchdemo.dto.MerchantDto(m.merchantId, m.phone, m.provider) from Merchant m", MerchantDto.class)
            .setMaxResults(5)
            .getResultList();
  }

  public List<MerchantAggregateDto> findMerchantDataByProvider() {
    return entityManager
            .createQuery("select new com.batchexample.batchdemo.dto.MerchantAggregateDto(m.provider, count(m)) from Merchant m group by m.provider", MerchantAggregateDto.class)
            .getResultList();
  }

  //  public HibernateCursorItemReader<MerchantDto> readDataUsingCursor() {
  //    return
  //        new HibernateCursorItemReaderBuilder<MerchantDto>()
  //            .name("creditReader")
  //            .sessionFactory(sessionFactory)
  //            .queryString("from Merchant")
  //            .build();
  //  }
}
