package com.aprzybysz.library.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorRatingDTO implements Comparable<AuthorRatingDTO> {

  private String author;
  private Double averageRating;

  @Override
  public int compareTo(AuthorRatingDTO o) {
    return averageRating > o.averageRating ? 1 : -1;
  }
}
