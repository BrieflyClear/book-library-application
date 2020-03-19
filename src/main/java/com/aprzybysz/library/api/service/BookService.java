package com.aprzybysz.library.api.service;

import com.aprzybysz.library.api.dto.AuthorRatingDTO;
import com.aprzybysz.library.data.JsonParser;
import com.aprzybysz.library.data.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

  public List<Book> findAll() {
    return JsonParser.getInstance().readFromFileAll();
  }

  public Optional<Book> findByIsbn(String isbn) {
    var list = findAll().stream().filter(it -> it.getIsbn().equals(isbn)).collect(Collectors.toList());
    if(list.size() == 1) {
      return Optional.ofNullable(list.get(0));
    } else {
      return Optional.empty();
    }
  }

  public List<Book> findByCategory(String category) {
    List<Book> books = new ArrayList<>();
    findAll().forEach(it -> {
      var array = it.getCategories();
      Arrays.sort(array);
      if(Arrays.binarySearch(array, category) >= 0) {
        books.add(it);
      }
    });
    return books;
  }

  public List<AuthorRatingDTO> getAuthorsRatings() {
    List<Book> books = findAll();
    Set<String> authors = new HashSet<>(Collections.emptySet());
    books.forEach(it -> authors.addAll(Arrays.asList(it.getAuthors())));
    List<AuthorRatingDTO> ratings = new ArrayList<>();
    authors.forEach(it -> ratings.add(new AuthorRatingDTO(it, 0.0)));
    ratings.forEach(it -> {
      var authorsBooksList = books.stream()
          .filter(g -> Arrays.asList(g.getAuthors()).contains(it.getAuthor())).collect(Collectors.toList());
      double sum = 0;
      int count = 0;
      for(Book book : authorsBooksList) {
        if(book.getRatingsCount() > 0) {
          sum += book.getAverageRating() * book.getRatingsCount();
          count += book.getRatingsCount();
        }
      }
      if(count == 0) {
        it.setAverageRating(0.0);
      } else {
        it.setAverageRating(BigDecimal.valueOf(sum / count).setScale(2, RoundingMode.HALF_UP).doubleValue());
      }
    });
    ratings.removeIf(g -> g.getAverageRating() == 0);
    return ratings;
  }
}
