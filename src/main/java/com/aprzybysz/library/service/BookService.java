package com.aprzybysz.library.service;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.data.provider.DataProvider;
import com.aprzybysz.library.data.util.IAverageRatingCalculator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

  private DataProvider dataProvider;

  private IAverageRatingCalculator calculatorStrategy;

  public BookService(DataProvider dataProvider, IAverageRatingCalculator calculator) {
    this.dataProvider = dataProvider;
    this.calculatorStrategy = calculator;
  }

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
      if(category != null) {
        var array = it.getCategories();
        if(array != null) {
          Arrays.sort(array);
          if(Arrays.binarySearch(array, category) >= 0) {
            books.add(it);
          }
        }
      } else {
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

  public List<String> getCategories() {
    List<Book> books = dataProvider.getAllBooks();
    Set<String> categories = new HashSet<>();
    books.forEach(it -> {
      if(it.getCategories() != null) {
        categories.addAll(Arrays.asList(it.getCategories()));
      }
    });
    return new ArrayList<>(categories);
  }
}
