package com.aprzybysz.library.data.util;

import com.aprzybysz.library.data.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IAverageRatingCalculator {

  Double calculate(List<Book> books, String author);
}
