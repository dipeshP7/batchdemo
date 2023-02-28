package com.batchexample.batchdemo.service;

import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.entity.Merchant;
import com.batchexample.batchdemo.repository.MerchantRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Log4j2
@Service
@Component
public class MerchantService {
    @Autowired private MerchantRepository merchantRepository;
    private List<MerchantDto> merchantList;

    public List<MerchantDto> getMerchants(){
        try{
        merchantList = merchantRepository.findAllMerchants();
        log.info("merchant list 1 : {}", merchantList);
        } catch(Exception e){
            throw new RuntimeException("Exception occurred while getting merchants list" +  e.getMessage());
        }
        return merchantList;
    }

    public Iterable<Merchant> getMerchantList(){
        Iterable<Merchant> merchants;
        try{
            merchants = merchantRepository.findAll();
            log.info("merchant list 2 {}", merchants);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Exception occurred while getting merchants list " + e.getMessage());
        }
        return merchants;
    }

  public static void main(String[] args) {
    //
      MerchantService service = new MerchantService();
      service.getMerchantList();
      service.getMerchants();
  }
}
