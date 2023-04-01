package com.batchexample.batchdemo.chunktask;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import java.util.List;

@Log4j2
public class SampleTextWriter implements ItemWriter<String> {

  @Override
  public void write(List<? extends String> messages) throws Exception {
    log.info("in write method");
    for (String msg : messages) {
      log.info("Writing the data " + msg);
    }
  }
}
