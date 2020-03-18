package com.aprzybysz.library.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorRatingDTO implements Comparable<AuthorRatingDTO> {

  private String author;
  private double averageRating;

  @Override
  public int compareTo(AuthorRatingDTO o) {
    return averageRating > o.averageRating ? 1 : -1;
  }
}
