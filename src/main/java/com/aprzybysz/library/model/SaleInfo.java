package com.aprzybysz.library.model;

import javax.security.auth.message.callback.PrivateKeyCallback;
import java.math.BigDecimal;

public class SaleInfo {

  private String country;
  private /*SaleabilityEnum*/ String saleability;
  private boolean isEbook;
  private Offer[] offers;
  private String buyLink;
  private Price listPrice;
  private Price retailPrice;

  private class Price {
    private BigDecimal amount;
    private String currencyCode;
  }
}
