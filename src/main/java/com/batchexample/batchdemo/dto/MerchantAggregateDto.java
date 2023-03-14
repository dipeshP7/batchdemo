package com.batchexample.batchdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MerchantAggregateDto {
    private String provider;
    private long total;
}
