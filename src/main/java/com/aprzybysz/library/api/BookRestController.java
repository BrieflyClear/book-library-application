package com.aprzybysz.library.api;

import com.aprzybysz.library.api.exceptions.BookNotFoundException;
import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.api.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
  public Book getByIsbn(@PathVariable("isbn") String isbn) {
    return service.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
  }

  @GetMapping("/category/{category}")
  public List<Book> getByCategory(@PathVariable("category") String category) {
    return service.findByCategory(category);
  }
}
