package com.aprzybysz.library.data.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

  private String isbn; // book's unique identifier
  private String title;
  private String subtitle;
  private String publisher;
  private long publishedDate; // UNIX timestamp
  private String description;
  private int pageCount;
  private String thumbnailUrl;
  private String language;
  private String previewLink;
  private double averageRating;
  private int ratingsCount;
  private String[] authors;
  private String[] categories;
}
