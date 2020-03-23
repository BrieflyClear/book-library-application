package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.api.dto.BookDTO;
import com.aprzybysz.library.api.exceptions.BookNotFoundException;
import com.aprzybysz.library.api.mapper.BookMapper;
import com.aprzybysz.library.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

  private BookService service;

  private BookMapper mapper;

  public BookRestController(BookService service, BookMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public List<BookDTO> getAll(){
    return service.findAll().stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }

  @GetMapping("/isbn/{isbn}")
  public BookDTO getByIsbn(@PathVariable("isbn") String isbn) {
    return service.findByIsbn(isbn).map(mapper::bookToBookDTO)
        .orElseThrow(() -> new BookNotFoundException(isbn));
  }

  @GetMapping("/category/{category}")
  public List<BookDTO> getByCategory(@PathVariable("category") String category) {
    return service.findByCategory(category).stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }
}
