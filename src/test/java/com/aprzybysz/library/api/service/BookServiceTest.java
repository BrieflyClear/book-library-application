package com.aprzybysz.library.api.service;

import com.aprzybysz.library.data.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

  @Test
  void averageOfFirstThreeBooksFromDefaultDataSourceShouldReturn421() {
    var books = JsonParser.getInstance().readFromExternalJsonFile();
    var limited = books.subList(0, 3);
    double result = new BookService().calculateAverageRating(limited);
    assertEquals(4.21, result);
  }
}