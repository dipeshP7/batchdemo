package com.batchexample.batchdemo.controller;

import com.batchexample.batchdemo.exception.CustomBatchException;
import com.batchexample.batchdemo.utils.TaskName;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** Main Rest Controller Use @Log4j2 for logging Request should start with /task/{taskName} */
@Log4j2
@RestController
@RequestMapping("/task/{taskName}")
public class RestBatchCotroller {

  @Autowired JobLauncher jobLauncher;

  @Autowired Job processJob;

  @Autowired Job processChunkSampleJob;

  /**
   * This is the test Rest controller
   *
   * @return
   */
  @GetMapping("/test")
  public String initRestBatchController() {
    log.info("calling in test controller");
    return "Batch demo initiated";
  }

  /**
   * This is the tasklet rest controller rest request example. This method will use tasklet and
   * process the job
   *
   * @param taskName name of the task.
   * @return String as result of batch
   */
  @RequestMapping(value = "/taskletsample/run", method = RequestMethod.GET)
  public String executeSampleBatch(@PathVariable TaskName taskName) {
    try {
      /**
       * Here checking with provided param is equal with SAMPLETASKLET if other than SAMPLETASKLET
       * throw error
       */
      if (TaskName.SAMPLETASKLET != taskName) {
        log.error("Invalid task name");
        throw new CustomBatchException("Invalid task name");
      }
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

  /**
   * This is the Chunk rest controller rest request example. This method will use Chunk processing
   * and process the job
   *
   * @param taskName name of the task.
   * @return String as result of batch
   */
  @RequestMapping(value = "/chunksample/run", method = RequestMethod.GET)
  public String executeSampleChunkBatch(@PathVariable TaskName taskName) {
    try {
      /**
       * Here checking with provided param is equal with SAMPLECHUNKTEXT if other than
       * SAMPLECHUNKTEXT throw error
       */
      if (TaskName.SAMPLECHUNKTEXT != taskName) {
        log.error("Invalid task name");
        throw new CustomBatchException("Invalid task name");
      }
      log.info("calling chunk batch example");
      JobParameters jobParameters =
          new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
      JobExecution execution = jobLauncher.run(processChunkSampleJob, jobParameters);
      log.info("status {}", execution.getStatus());
      if (execution.getStatus().equals(BatchStatus.COMPLETED)) {
        return "sample batch executed successfully";
      } else {
        return "something went wrong! please try again";
      }
    } catch (JobExecutionException e) {
      throw new CustomBatchException(e.getMessage(), e);
    }
  }
}
