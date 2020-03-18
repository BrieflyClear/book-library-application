package com.aprzybysz.library.api;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.data.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionService;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookRestController {

  @Autowired
  private BookService service;

  @GetMapping
  public List<Book> getAll(){
    return service.findAll();
  }

  @GetMapping("/isbn/{isbn}")
  public List<Book> getByIsbn(@PathVariable("isbn") String isbn) {
    return service.findByIsbn(isbn);
  }

  @GetMapping("/category/{category}")
  public List<Book> getByCategory(@PathVariable("category") String category) {
    return service.findByCategory(category);
  }
}
