package com.batchexample.batchdemo.controller;

import com.batchexample.batchdemo.exception.CustomBatchException;
import com.batchexample.batchdemo.service.MerchantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/task/{taskName}")
public class RestBatchCotroller {

  @Autowired JobLauncher jobLauncher;

  @Autowired Job processJob;

  @GetMapping("/test")
  public String initRestBatchController() {
    log.info("calling in test controller");
    return "Batch demo initiated";
  }

  @GetMapping("/run")
  public String executeSampleBatch() {
    try {
      JobParameters jobParameters =
          new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
      JobExecution execution = jobLauncher.run(processJob, jobParameters);
      log.info("status {}", execution.getStatus());
      if (execution.getStatus().equals(BatchStatus.COMPLETED)) {
        return "sample batch executed successfully";
      } else {
        return "something went wrong! please try again";
      }
    } catch (CustomBatchException e) {
      log.warn(e.getMessage());
      return "Internal error unable to execute the batch";
    } catch (JobInstanceAlreadyCompleteException
        | JobExecutionAlreadyRunningException
        | JobParametersInvalidException e) {
      throw new RuntimeException(e);
    } catch (JobRestartException e) {
      throw new RuntimeException(e);
    }
  }
}
