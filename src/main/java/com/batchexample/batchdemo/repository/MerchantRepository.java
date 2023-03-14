package com.batchexample.batchdemo.repository;

import com.batchexample.batchdemo.dto.MerchantAggregateDto;
import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.entity.Merchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MerchantRepository extends CrudRepository<Merchant, String> {

    @Query(value = "select new com.batchexample.batchdemo.dto.MerchantDto(m.merchantId, m.phone, m.provider) from Merchant m")
    List<MerchantDto> findAllMerchants();

  @Query(
      value =
          "select new com.batchexample.batchdemo.dto.MerchantAggregateDto(m.provider, count(m)) from Merchant m group by m.provider")
  List<MerchantAggregateDto> findMerchantByProvider();
}
