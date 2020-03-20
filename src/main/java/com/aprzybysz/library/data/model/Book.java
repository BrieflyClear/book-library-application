package com.aprzybysz.library.data.model;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Book {

  private String isbn; // book's unique identifier
  private String title;
  private String subtitle;
  private String publisher;
  private long publishedDate; // UNIX timestamp in milliseconds
  private String description;
  private int pageCount;
  private String thumbnailUrl;
  private String language;
  private String previewLink;
  private double averageRating;
  private int ratingsCount;
  private String[] authors;
  private String[] categories;

  public static BookBuilder builder() {
    return new BookBuilder();
  }

  public static final class BookBuilder {
    Book book = new Book();

    public BookBuilder() { }

    public Book create() {
      if(book.isbn == null || book.title == null) {
        throw new IllegalArgumentException();
      }
      return book;
    }

    public BookBuilder isbn(String isbn) {
      book.isbn = isbn;
      return this;
    }
    public BookBuilder title(String title) {
      book.title = title;
      return this;
    }
    public BookBuilder subtitle(String subtitle) {
      book.subtitle = subtitle;
      return this;
    }
    public BookBuilder publisher(String publisher) {
      book.publisher = publisher;
      return this;
    }
    public BookBuilder description(String value) {
      book.description = value;
      return this;
    }
    public BookBuilder thumbnailUrl(String value) {
      book.thumbnailUrl = value;
      return this;
    }
    public BookBuilder language(String value) {
      book.language = value;
      return this;
    }
    public BookBuilder previewLink(String value) {
      book.previewLink = value;
      return this;
    }
    public BookBuilder authors(String[] value) {
      book.authors = value;
      return this;
    }
    public BookBuilder categories(String[] value) {
      book.categories = value;
      return this;
    }
    public BookBuilder publishedDate(long unixValue) {
      book.publishedDate = unixValue;
      return this;
    }
    public BookBuilder pageCount(int value) {
      book.pageCount = value;
      return this;
    }
    public BookBuilder averageRating(double rating) {
      book.averageRating = rating;
      return this;
    }
    public BookBuilder ratingsCount(int value) {
      book.ratingsCount = value;
      return this;
    }
  }
}
