package com.aprzybysz.library.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorRatingDTO implements Comparable<AuthorRatingDTO> {

  private String author;
  private Double averageRating;

  @Override
  public int compareTo(AuthorRatingDTO o) {
    if(averageRating == null) {
      return -1;
    }
    if(o.averageRating == null) {
      return 1;
    }
    return averageRating > o.averageRating ? 1 : -1;
  }
}
