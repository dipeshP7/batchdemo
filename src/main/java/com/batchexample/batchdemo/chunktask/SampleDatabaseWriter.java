package com.batchexample.batchdemo.chunktask;

import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.entity.Merchant;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class SampleDatabaseWriter implements ItemWriter<Merchant> {

  @Override
  public void write(List<? extends Merchant> merchants) throws Exception {
    if (!CollectionUtils.isEmpty(merchants)) {
      log.info("writing : {}", merchants);
    } else {
      log.warn("list is empty or null");
    }
  }
}
