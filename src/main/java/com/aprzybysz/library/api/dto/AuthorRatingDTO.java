package com.aprzybysz.library.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorRatingDTO implements Comparable<AuthorRatingDTO> {

  private final String author;
  private final Double averageRating;

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

  public static AuthorRatingDTO.AuthorRatingDTOBuilder builder() {
    return new AuthorRatingDTOBuilder();
  }

  public static final class AuthorRatingDTOBuilder {
    String author;
    Double avg;

    public AuthorRatingDTO create() {
      return new AuthorRatingDTO(author, avg);
    }
    public AuthorRatingDTOBuilder author(String value) {
      author = value;
      return this;
    }
    public AuthorRatingDTOBuilder averageRating(Double value) {
      avg = value;
      return this;
    }
  }
}
