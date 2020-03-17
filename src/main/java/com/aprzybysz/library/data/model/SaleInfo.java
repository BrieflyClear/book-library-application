package com.aprzybysz.library.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SaleInfo {

  private String country;
  private /*SaleabilityEnum*/ String saleability;
  private boolean isEbook;
  private Offer[] offers;
  private String buyLink;
  private Price listPrice;
  private Price retailPrice;

  @Data
  @AllArgsConstructor
  private class Price {
    private BigDecimal amount;
    private String currencyCode;
  }
}
