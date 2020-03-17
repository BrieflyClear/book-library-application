package com.aprzybysz.library.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Offer {

  private int finskyOfferType;
  private PriceInMicros listPrice;
  private PriceInMicros retailPrice;

  @Data
  @AllArgsConstructor
  private class PriceInMicros {
    private String amountInMicros;
    private String currencyCode;
  }
}
