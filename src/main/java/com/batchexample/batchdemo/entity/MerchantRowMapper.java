package com.batchexample.batchdemo.entity;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MerchantRowMapper implements RowMapper<Merchant> {

  @Override
  public Merchant mapRow(ResultSet rs, int rowNum) throws SQLException {
     return new Merchant();
//    return Merchant.builder()
//        .merchantId(rs.getString("merchantId"))
//        .phone(rs.getString("phone"))
//        .provider(rs.getString("provider"))
//        .build();
  }
}
