package com.aprzybysz.library.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;

@Data
@AllArgsConstructor
public class AuthorRating implements Comparable<AuthorRating> {

  private String author;
  private double averageRating;

  @Override
  public int compareTo(AuthorRating o) {
    return averageRating > o.averageRating ? 1 : -1;
  }
}
