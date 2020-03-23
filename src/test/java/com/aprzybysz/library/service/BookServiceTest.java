package com.aprzybysz.library.service;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.data.provider.DataProvider;
import com.aprzybysz.library.data.util.AverageRatingCalculatorImpl;
import com.aprzybysz.library.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookServiceTest {

  private static BookService service;

  private static List<Book> list;

  @BeforeAll
  static void initArray() {
    DataProvider dataProvider = Mockito.mock(DataProvider.class);
    service = new BookService(dataProvider, new AverageRatingCalculatorImpl());
    list = new ArrayList<>();
    list.add(new Book.BookBuilder().isbn("9781592432171").title("AI")
        .authors(new String[]{"Andre"}).averageRating(4.0).ratingsCount(4).create());
    list.add(new Book.BookBuilder().isbn("9781592432172").title("Microservices")
        .authors(new String[]{"Andre"}).averageRating(3.0).ratingsCount(3).create());
    list.add(new Book.BookBuilder().isbn("9781592432173").title("Clean code")
        .authors(new String[]{"Andrew"}).averageRating(4.0).ratingsCount(4).create());
    list.add(new Book.BookBuilder().isbn("9781592432174").title("C++ for dummies")
        .categories(new String[]{"Programming"}).authors(new String[]{"Andre"}).create());
    list.add(new Book.BookBuilder().isbn("9781592432175").title("Mockito guide")
        .categories(new String[]{"Computers"}).authors(new String[]{"Andy"}).create());
    when(dataProvider.getAllBooks()).thenReturn(list);
  }

  @Test
  void shouldReturnMockedList() {
    var books = service.findAll();
    assertEquals(list, books);
  }

  @Test
  void shouldReturnBookWithGivenIsbnFromMockedList() {
    var book = service.findByIsbn("9781592432171");
    assertTrue(book.isPresent());
    assertEquals(list.get(0), book.get());
  }

  @Test
  void shouldReturnEmptyOptionalOnWrongIsbnFromMockedList() {
    var book = service.findByIsbn("9781592432170");
    assertFalse(book.isPresent());
    var book2 = service.findByIsbn("abc");
    assertFalse(book2.isPresent());
    var book3 = service.findByIsbn("");
    assertFalse(book3.isPresent());
  }

  @Test
  void shouldReturnBooksWithGivenCategoryAndEmptyListOnWrongCategory() {
    var booksOk = service.findByCategory("Programming");
    assertFalse(booksOk.isEmpty());
    assertEquals(1, booksOk.size());
    assertEquals(list.get(3), booksOk.get(0));

    var booksWrong = service.findByCategory("abc");
    assertTrue(booksWrong.isEmpty());
  }

  @Test
  void shouldReturnCalculatedRatingsWithoutNullValues() {
    var ratings = service.getAuthorsRatings();
    assertFalse(ratings.isEmpty());
    assertEquals(2, ratings.size());
    assertNotEquals(0, ratings.get("Andre"));
    assertNotEquals(0, ratings.get("Andrew"));
    assertNull(ratings.get("Andy"));
  }
}