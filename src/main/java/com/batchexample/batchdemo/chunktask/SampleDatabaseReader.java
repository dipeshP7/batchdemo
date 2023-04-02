package com.batchexample.batchdemo.chunktask;

import com.batchexample.batchdemo.entity.Merchant;
import com.batchexample.batchdemo.entity.MerchantRowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
@Component
public class SampleDatabaseReader extends JdbcCursorItemReader<Merchant>
    implements ItemReader<Merchant> {

  private static final String QUERY_GET_ALL_MERCHANT =
      "SELECT " + "merchant_id, " + "phone, " + "provider " + "FROM merchant ";

  public SampleDatabaseReader(@Autowired DataSource primaryDataSource) {
    setDataSource(primaryDataSource);
    setSql(QUERY_GET_ALL_MERCHANT);
    setFetchSize(100);
    setRowMapper(new MerchantRowMapper());
  }

  public class MerchantRowMapper implements RowMapper<Merchant> {
    @Override
    public Merchant mapRow(ResultSet rs, int rowNum) throws SQLException {
      Merchant merchant = new Merchant();
      merchant.setMerchantId(rs.getString("merchant_id"));
      merchant.setPhone(rs.getString("phone"));
      merchant.setProvider(rs.getString("provider"));
      return merchant;
    }
  }
}
