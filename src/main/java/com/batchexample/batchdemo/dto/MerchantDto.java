package com.batchexample.batchdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MerchantDto {
    private String merchantId;
    private String phone;
    private String provier;
}
