package com.aprzybysz.library.model;

import java.math.BigDecimal;
import java.util.Set;

public class VolumeInfo {

  private String title;
  private String subtitle;
  private Set<String> authors;
  private String publisher;
  private String description;
  private String publishedDate;
  private Set<IndustryIdentifier> industryIdentifiers;
  private ReadingMode readingModes;
  private int pageCount;
  private /*PrintTypeEnum*/ String printType;
  private Set<String> categories;
  private BigDecimal averageRating;
  private int ratingsCount;
  private /*MaturityRatingEnum*/ String maturityRating;
  private boolean allowAnonLogging;
  private String contentVersion;
  private String language;
  private String previewLink;
  private String infoLink;
  private String canonicalVolumeLink;
  private SaleInfo saleInfo;
  private AccessInfo accessInfo;
  private SearchInfo searchInfo;
}
