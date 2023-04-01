package com.batchexample.batchdemo.chunktask;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

@Log4j2
public class SampleTextReader implements ItemReader {

  private static final String[] MESSAGE = {
    "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
  };

  private int COUNT = 0;

  @Override
  public Object read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    log.info("in read method");
    if (COUNT < MESSAGE.length) {
      return MESSAGE[COUNT++];
    } else {
      COUNT = 0;
    }
    return null;
  }
}
