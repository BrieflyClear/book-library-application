package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.api.dto.BookDTO;
import com.aprzybysz.library.api.exceptions.BookNotFoundException;
import com.aprzybysz.library.api.mapper.BookMapper;
import com.aprzybysz.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
@AllArgsConstructor
public class BookRestController {

  @Autowired
  private BookService service;

  @Autowired
  private BookMapper mapper;

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
