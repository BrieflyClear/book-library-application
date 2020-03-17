package com.aprzybysz.library.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

  private String kind;
  private String id;
  private String etag;
  private String selfLink;
  private VolumeInfo volumeInfo;
}
