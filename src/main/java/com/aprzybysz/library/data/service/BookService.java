package com.aprzybysz.library.data.service;

import com.aprzybysz.library.data.model.AuthorRating;
import com.aprzybysz.library.data.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

  public List<Book> findAll() {
    return Collections.emptyList();
  }

  public List<Book> findByAuthor(String author) {
    return Collections.emptyList();
  }

  public List<Book> findByIsbn(String isbn) {
    return Collections.emptyList();
  }

  public List<Book> findByCategory(String category) {
    return Collections.emptyList();
  }

  public List<AuthorRating> getAuthorsRatings() {
    List<Book> books = findAll();
    Set<String> authors = new HashSet<>(Collections.emptySet());
    books.forEach(it -> authors.addAll(Arrays.asList(it.getAuthors())));
    List<AuthorRating> ratings = new ArrayList<>(Collections.emptyList());
    authors.forEach(it -> ratings.add(new AuthorRating(it, 0.0)));
    ratings.forEach(it -> {
      var list = books.stream().filter(g -> Arrays.asList(g.getAuthors()).contains(it.getAuthor()))
          .collect(Collectors.toList());
      int bookToCount = 0;
      for(int i = 1; i <= list.size(); i++) {
        double bookRating = list.get(i-1).getAverageRating();
        if(bookRating != 0) {
          it.setAverageRating((it.getAverageRating() + bookToCount) / bookToCount);
          bookToCount++;
        }
      }
    });
    return ratings;
  }
}
