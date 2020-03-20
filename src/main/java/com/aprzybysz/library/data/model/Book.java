package com.aprzybysz.library.data.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Book {

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
  private final Integer ratingsCount;
  private final String[] authors;
  private final String[] categories;

  public static BookBuilder builder() {
    return new BookBuilder();
  }

  public static final class BookBuilder {
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
    private Integer ratingsCount;
    private String[] authors;
    private String[] categories;

    public BookBuilder() { }

    public Book create() {
      if(this.isbn == null || this.title == null) {
        throw new IllegalArgumentException();
      }
      return new Book(isbn, title, subtitle, publisher, publishedDate, description, pageCount, thumbnailUrl,
          language, previewLink, averageRating, ratingsCount, authors, categories);
    }

    public BookBuilder isbn(String isbn) {
      this.isbn = isbn;
      return this;
    }
    public BookBuilder title(String title) {
      this.title = title;
      return this;
    }
    public BookBuilder subtitle(String subtitle) {
      this.subtitle = subtitle;
      return this;
    }
    public BookBuilder publisher(String publisher) {
      this.publisher = publisher;
      return this;
    }
    public BookBuilder description(String value) {
      this.description = value;
      return this;
    }
    public BookBuilder thumbnailUrl(String value) {
      this.thumbnailUrl = value;
      return this;
    }
    public BookBuilder language(String value) {
      this.language = value;
      return this;
    }
    public BookBuilder previewLink(String value) {
      this.previewLink = value;
      return this;
    }
    public BookBuilder authors(String[] value) {
      this.authors = value;
      return this;
    }
    public BookBuilder categories(String[] value) {
      this.categories = value;
      return this;
    }
    public BookBuilder publishedDate(Long unixValue) {
      this.publishedDate = unixValue;
      return this;
    }
    public BookBuilder pageCount(Integer value) {
      this.pageCount = value;
      return this;
    }
    public BookBuilder averageRating(Double rating) {
      this.averageRating = rating;
      return this;
    }
    public BookBuilder ratingsCount(Integer value) {
      this.ratingsCount = value;
      return this;
    }
  }
}
