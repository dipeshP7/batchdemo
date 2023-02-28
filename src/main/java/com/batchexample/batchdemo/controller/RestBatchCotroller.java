package com.batchexample.batchdemo.controller;

import com.batchexample.batchdemo.dto.MerchantDto;
import com.batchexample.batchdemo.entity.Merchant;
import com.batchexample.batchdemo.service.MerchantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/task/{taskName}")
public class RestBatchCotroller {
    @Autowired private MerchantService merchantService;

    @GetMapping("/test")
    public String initRestBatchController(){
        log.info("calling in test controller");
        return "Batch demo initiated";
    }

    @GetMapping("/run")
    public String executeSampleBatch(){
        try{
           List<MerchantDto> merchantList = merchantService.getMerchants();
           Iterable<Merchant> mList = merchantService.getMerchantList();
            if(merchantList.size() > 0){
                return "sample batch executhed successfully";
            } else {
                return "something went wrong! please try again";
            }
        }catch(Exception e){
            return "Unable to executhe the batch error : " + e.getMessage();
        }
    }
}
