package com.aprzybysz.library.service;

import com.aprzybysz.library.data.util.IAverageRatingCalculator;
import com.aprzybysz.library.data.DataProvider;
import com.aprzybysz.library.data.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

  @Autowired
  private DataProvider dataProvider;

  @Autowired
  private IAverageRatingCalculator calculatorStrategy;

  public List<Book> findAll() {
    return dataProvider.getAllBooks();
  }

  public Optional<Book> findByIsbn(String isbn) {
    var list = dataProvider.getAllBooks().stream().filter(it -> it.getIsbn().equals(isbn)).collect(Collectors.toList());
    if(list.size() == 1) {
      return Optional.ofNullable(list.get(0));
    } else {
      return Optional.empty();
    }
  }

  public List<Book> findByCategory(String category) {
    List<Book> books = new ArrayList<>();
    dataProvider.getAllBooks().forEach(it -> {
      var array = it.getCategories();
      Arrays.sort(array);
      if(Arrays.binarySearch(array, category) >= 0) {
        books.add(it);
      }
    });
    return books;
  }

  public Map<String, Double> getAuthorsRatings() {
    List<Book> books = dataProvider.getAllBooks();
    Set<String> authors = new HashSet<>();
    Map<String, Double> ratings = new HashMap<>();
    books.removeIf(g -> g.getAuthors() == null);
    books.forEach(it -> authors.addAll(Arrays.asList(it.getAuthors())));
    authors.forEach(it -> {
      Double avg = calculatorStrategy.calculate(books, it);
      if(avg != null) {
        ratings.put(it, avg);
      }
    });
    return ratings;
  }
}
