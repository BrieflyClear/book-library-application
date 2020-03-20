package com.aprzybysz.library.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
  private String isbn; // bookDTO's unique identifier
  private String title;
  private String subtitle;
  private String publisher;
  private Long publishedDate; // UNIX timestamp in milliseconds
  private String description;
  private Integer pageCount;
  private String thumbnailUrl;
  private String language;
  private String previewLink;
  private Double averageRating;
  private String[] authors;
  private String[] categories;

  public static BookDTO.BookDTOBuilder builder() {
    return new BookDTOBuilder();
  }

  public static final class BookDTOBuilder {
    BookDTO bookDTO = new BookDTO();

    public BookDTOBuilder() { }

    public BookDTO create() {
      if(bookDTO.isbn == null || bookDTO.title == null) {
        throw new IllegalArgumentException();
      }
      return bookDTO;
    }

    public BookDTOBuilder isbn(String isbn) {
      bookDTO.isbn = isbn;
      return this;
    }
    public BookDTOBuilder title(String title) {
      bookDTO.title = title;
      return this;
    }
    public BookDTOBuilder subtitle(String subtitle) {
      bookDTO.subtitle = subtitle;
      return this;
    }
    public BookDTOBuilder publisher(String publisher) {
      bookDTO.publisher = publisher;
      return this;
    }
    public BookDTOBuilder description(String value) {
      bookDTO.description = value;
      return this;
    }
    public BookDTOBuilder thumbnailUrl(String value) {
      bookDTO.thumbnailUrl = value;
      return this;
    }
    public BookDTOBuilder language(String value) {
      bookDTO.language = value;
      return this;
    }
    public BookDTOBuilder previewLink(String value) {
      bookDTO.previewLink = value;
      return this;
    }
    public BookDTOBuilder authors(String[] value) {
      bookDTO.authors = value;
      return this;
    }
    public BookDTOBuilder categories(String[] value) {
      bookDTO.categories = value;
      return this;
    }
    public BookDTOBuilder publishedDate(Long unixValue) {
      bookDTO.publishedDate = unixValue;
      return this;
    }
    public BookDTOBuilder pageCount(Integer value) {
      bookDTO.pageCount = value;
      return this;
    }
    public BookDTOBuilder averageRating(Double rating) {
      bookDTO.averageRating = rating;
      return this;
    }
  }
}
