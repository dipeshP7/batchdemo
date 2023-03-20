package com.batchexample.batchdemo.tasks;

import com.batchexample.batchdemo.dto.MerchantAggregateDto;
import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.exception.CustomBatchException;
import com.batchexample.batchdemo.repository.MerchantRepository;
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
public class SampleBatchWithRepositoryTasklet implements Tasklet {

  @Autowired MerchantRepository merchantRepository;

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

      for (int listSize = ConstantNumber.ZERO; listSize < merchantAggregateDto.size(); listSize++) {
        MerchantAggregateDto dto = merchantAggregateDto.get(listSize);
        log.info("{} : {}", dto.getProvider(), dto.getTotal());
      }

    } catch (CustomBatchException | NullPointerException e) {
      log.info("unable to process the batch error : {}", e.getMessage());
      throw new CustomBatchException(e.getMessage());
    }
    return RepeatStatus.FINISHED;
  }
}
