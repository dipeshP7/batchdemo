package com.batchexample.batchdemo.tasks;

import com.batchexample.batchdemo.dto.MerchantDto;import com.batchexample.batchdemo.exception.CustomBatchException;import com.batchexample.batchdemo.repository.MerchantRepository;import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;import java.util.List;

@Log4j2
public class SampleBatchTasklet implements Tasklet {

 private MerchantRepository merchantRepository;

 public SampleBatchTasklet(MerchantRepository merchantRepository){
   this.merchantRepository = merchantRepository;
 }
  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
      throws Exception {
    try{
    log.info("calling in smaple batch tasklet");
    List<MerchantDto> merchantDtoList = merchantRepository.findAllMerchants();
    log.info(merchantDtoList.size());
    }catch (Exception e){
      log.info("unable to process the batch error : {}",e.getMessage());
      throw new CustomBatchException(e.getMessage());
    }
    return RepeatStatus.FINISHED;
  }
}
