package com.batchexample.batchdemo.chunktask;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;

@Log4j2
public class SampleTextProcessor implements ItemProcessor<String, String> {

  @Override
  public String process(String data) throws Exception {
    log.info("in process method {}", data);
    return data.toUpperCase();
  }
}
