package com.aprzybysz.library.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

  public static AuthorRatingDTO.AuthorRatingDTOBuilder builder() {
    return new AuthorRatingDTOBuilder();
  }

  public static final class AuthorRatingDTOBuilder {
    AuthorRatingDTO dto = new AuthorRatingDTO();

    public AuthorRatingDTO create() {
      return dto;
    }
    public AuthorRatingDTOBuilder author(String value) {
      dto.author = value;
      return this;
    }
    public AuthorRatingDTOBuilder averageRating(Double value) {
      dto.averageRating = value;
      return this;
    }
  }
}
