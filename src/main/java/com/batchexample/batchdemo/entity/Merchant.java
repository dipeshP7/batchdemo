package com.batchexample.batchdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Data
public class Merchant {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "merchant_id")
  private String merchantId;

  @Column(name = "phone")
  private String phone;

  @Column(name = "provider")
  private String provider;
}
