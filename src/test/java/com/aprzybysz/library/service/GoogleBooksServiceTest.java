package com.aprzybysz.library.service;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.data.provider.DataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

class GoogleBooksServiceTest {

  private static GoogleBooksService service;

  private static List<Book> list;

  @BeforeAll
  static void initArray() {
    DataProvider dataProvider = Mockito.mock(DataProvider.class);
    service = new GoogleBooksService(dataProvider);
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
    when(dataProvider.getBooksFromGoogle("9781592432171", null, null)).thenReturn(List.of(list.get(0)));
    when(dataProvider.getBooksFromGoogle("9", null, null)).thenReturn(list);
    when(dataProvider.getBooksFromGoogle("9", 2, null)).thenReturn(list.subList(2, 4));
    when(dataProvider.getBooksFromGoogle("9", null, 2)).thenReturn(list.subList(0, 2));
    when(dataProvider.getBooksFromGoogle("9", 2, 2)).thenReturn(list.subList(2, 4));
  }

  @Test
  void findBySearchShouldReturnListsForGivenValues() {
    var books = service.findFromGoogle("9781592432171");
    assertFalse(books.isEmpty());
    assertEquals(1, books.size());
    assertEquals(list.get(0), books.get(0));
    var books2 = service.findFromGoogle("9");
    assertEquals(5, books2.size());
    assertEquals(list, books2);
  }

  @Test
  void shouldReturnListsForGivenValuesLimitedTo1Record() {
    var books = service.findFromGoogleWithLimit("9", 2);
    assertEquals(2, books.size());
  }

  @Test
  void shouldReturnListsForGivenValuesStartingFromSecondRecord() {
    var books = service.findFromGoogleWithIndex("9", 2);
    assertEquals(2, books.size());
    assertEquals(list.get(2), books.get(0));
  }

  @Test
  void shouldReturnListsForGivenValuesStartingFromSecondRecordLimitedTo2Records() {
    var books = service.findFromGoogleWithIndexAndLimit("9", 2, 2);
    assertEquals(2, books.size());
    assertEquals(List.of(list.get(2), list.get(3)), books);
  }
}