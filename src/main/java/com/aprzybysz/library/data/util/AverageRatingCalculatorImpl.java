package com.aprzybysz.library.data.util;

import com.aprzybysz.library.data.model.Book;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class AverageRatingCalculatorImpl implements IAverageRatingCalculator {

  @Override
  public Double calculate(List<Book> books, String author) {
    double sum = 0;
    int count = 0;
    List<Book> authorsBooks = books.stream().filter(it -> Arrays.asList(it.getAuthors()).contains(author)).collect(Collectors.toList());
    for(Book book : authorsBooks) {
      if(book.getRatingsCount() != null) {
        sum += book.getAverageRating() * book.getRatingsCount();
        count += book.getRatingsCount();
      }
    }
    if(count == 0) {
      return null;
    } else {
      return (BigDecimal.valueOf(sum / count).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
  }
}
