package com.aprzybysz.library.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
  private final String isbn; // bookDTO's unique identifier
  private final String title;
  private final String subtitle;
  private final String publisher;
  private final Long publishedDate; // UNIX timestamp in milliseconds
  private final String description;
  private final Integer pageCount;
  private final String thumbnailUrl;
  private final String language;
  private final String previewLink;
  private final Double averageRating;
  private final String[] authors;
  private final String[] categories;

  public static BookDTO.BookDTOBuilder builder() {
    return new BookDTOBuilder();
  }

  public static final class BookDTOBuilder {
    private String isbn;
    private String title;
    private String subtitle;
    private String publisher;
    private Long publishedDate;
    private String description;
    private Integer pageCount;
    private String thumbnailUrl;
    private String language;
    private String previewLink;
    private Double averageRating;
    private String[] authors;
    private String[] categories;

    public BookDTOBuilder() { }

    public BookDTO create() {
      if(isbn == null || title == null) {
        throw new IllegalArgumentException();
      }
      return new BookDTO(isbn, title, subtitle, publisher, publishedDate, description, pageCount, thumbnailUrl,
          language, previewLink, averageRating, authors, categories);
    }

    public BookDTOBuilder isbn(String isbn) {
      this.isbn = isbn;
      return this;
    }
    public BookDTOBuilder title(String title) {
      this.title = title;
      return this;
    }
    public BookDTOBuilder subtitle(String subtitle) {
      this.subtitle = subtitle;
      return this;
    }
    public BookDTOBuilder publisher(String publisher) {
      this.publisher = publisher;
      return this;
    }
    public BookDTOBuilder description(String value) {
      this.description = value;
      return this;
    }
    public BookDTOBuilder thumbnailUrl(String value) {
      this.thumbnailUrl = value;
      return this;
    }
    public BookDTOBuilder language(String value) {
      this.language = value;
      return this;
    }
    public BookDTOBuilder previewLink(String value) {
      this.previewLink = value;
      return this;
    }
    public BookDTOBuilder authors(String[] value) {
      this.authors = value;
      return this;
    }
    public BookDTOBuilder categories(String[] value) {
      this.categories = value;
      return this;
    }
    public BookDTOBuilder publishedDate(Long unixValue) {
      this.publishedDate = unixValue;
      return this;
    }
    public BookDTOBuilder pageCount(Integer value) {
      this.pageCount = value;
      return this;
    }
    public BookDTOBuilder averageRating(Double rating) {
      this.averageRating = rating;
      return this;
    }
  }
}
