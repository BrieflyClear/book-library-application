package com.aprzybysz.library.model;

public class Offer {

  private int finskyOfferType;
  private PriceInMicros listPrice;
  private PriceInMicros retailPrice;

  private class PriceInMicros {
    private String amountInMicros;
    private String currencyCode;
  }
}
