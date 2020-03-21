package com.aprzybysz.library.data.util;

import com.aprzybysz.library.data.model.Book;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AverageRatingCalculatorImplTest {

  @Test
  void calculateTest() {
    List<Book> books = new ArrayList<>();
    String author = "AuthorTest";
    books.add(new Book.BookBuilder().isbn("2").title("test1").averageRating(3.5).ratingsCount(3).authors(new String[]{author}).create());
    books.add(new Book.BookBuilder().isbn("3").title("test2").averageRating(3.0).ratingsCount(7).authors(new String[]{author}).create());
    books.add(new Book.BookBuilder().isbn("4").title("test3").averageRating(5.0).ratingsCount(4).authors(new String[]{author}).create());
    books.add(new Book.BookBuilder().isbn("5").title("test4").averageRating(4.5).ratingsCount(3).authors(new String[]{"OtherAuthorTest"}).create());

    double expectedResult = BigDecimal.valueOf((3.5 * 3 + 3.0 * 7 + 5.0 * 4)/(3 + 7 + 4)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    AverageRatingCalculatorImpl calculator = new AverageRatingCalculatorImpl();
    Double result = calculator.calculate(books, author);
    assertNotNull(result);
    assertEquals(expectedResult, result);
  }
}