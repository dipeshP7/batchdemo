package com.batchexample.batchdemo.tasks;

import com.batchexample.batchdemo.dto.MerchantAggregateDto;
import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.utils.ConstantNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class SampleBatchWithEntityManagerTasklet implements Tasklet {

  @PersistenceContext EntityManager entityManager;

  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
      throws Exception {

    List<MerchantDto> merchantDtoList1 = findMerchantData();
    log.info("mdto1 size : {}", merchantDtoList1.size());

    List<MerchantAggregateDto> merchantAggregateDtoList = findMerchantDataByProvider();
    log.info("mAggdto1 size : {}", merchantAggregateDtoList.size());

    for (int listSize = ConstantNumber.ZERO;
        listSize < merchantAggregateDtoList.size();
        listSize++) {
      MerchantAggregateDto dto = merchantAggregateDtoList.get(listSize);
      log.info(" {} :  {}", dto.getProvider(), dto.getTotal());
    }

    return RepeatStatus.FINISHED;
  }

  public List<MerchantDto> findMerchantData() {
    return entityManager
        .createQuery(
            "select new com.batchexample.batchdemo.dto.MerchantDto(m.merchantId, m.phone, m.provider) from Merchant m",
            MerchantDto.class)
        .setMaxResults(5)
        .getResultList();
  }

  public List<MerchantAggregateDto> findMerchantDataByProvider() {
    return entityManager
        .createQuery(
            "select new com.batchexample.batchdemo.dto.MerchantAggregateDto(m.provider, count(m)) from Merchant m group by m.provider",
            MerchantAggregateDto.class)
        .getResultList();
  }
}
