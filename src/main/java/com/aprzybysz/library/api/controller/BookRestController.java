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

/**
 * Controls GET HTTP requests mapped by "/api/books". Returns data from DataProvider's cache from external JSON files
 * @author Andrzej Przybysz
 */
@RestController
@RequestMapping("/api/books")
public class BookRestController {

  private BookService service;

  private BookMapper mapper;

  public BookRestController(BookService service, BookMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  /**
   * Handles GET request for the main mapping
   * @return a list of all Books mapped to DTO by BookMapper
   */
  @GetMapping
  public List<BookDTO> getAll(){
    return service.findAll().stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }

  /**
   * Handles GET request for the "/isbn" mapping
   * @param isbn text value representing the ISBN of a book
   * @return a Book found by the given ISBN mapped to DTO by BookMapper.
   * If none is found, the 404 status code is returned.
   */
  @GetMapping("/isbn/{isbn}")
  public BookDTO getByIsbn(@PathVariable("isbn") String isbn) {
    return service.findByIsbn(isbn).map(mapper::bookToBookDTO)
        .orElseThrow(() -> new BookNotFoundException(isbn));
  }

  /**
   * Handles GET request for the "/category" mapping
   * @param category represents a value from book's categories array
   * @return a list Books found by the given category mapped to DTO by BookMapper
   */
  @GetMapping("/category/{category}")
  public List<BookDTO> getByCategory(@PathVariable("category") String category) {
    return service.findByCategory(category).stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }
}
