package com.batchexample.batchdemo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/task/{taskName}")
public class RestBatchCotroller {

    @GetMapping("/test")
    public String initRestBatchController(){
        log.info("calling in test controller");
        return "Batch demo initiated";
    }
}
