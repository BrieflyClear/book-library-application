package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.api.dto.BookDTO;
import com.aprzybysz.library.api.mapper.BookMapper;
import com.aprzybysz.library.service.GoogleBooksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls GET HTTP requests mapped by "/api/google". Returns data fetched from Google Books API.
 * Every method returns maximum 10 records. If one wants more, use requests with the "limit" and "startIndex" variables.
 * Every request should consists of the "search" variable as the Google API requires it.
 * @author Andrzej Przybysz
 */
@RestController
@RequestMapping("/api/google")
public class GoogleBooksController {

  private GoogleBooksService service;

  private BookMapper mapper;

  public GoogleBooksController(GoogleBooksService service, BookMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  /**
   * Handles GET request for the mapping by search values
   * @param value a value to search by in the Google Books API
   * @return a list of all Books mapped to DTO by BookMapper
   */
  @GetMapping("/search/{value}")
  public List<BookDTO> getGoogleBySearch(@PathVariable("value") String value) {
    return service.findFromGoogle(value).stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }

  /**
   * Handles GET request for the mapping by search values plus a starting index
   * @param value a value to search by in the Google Books API.
   * @param index a value of the starting index to display
   * @return a list of all Books mapped to DTO by BookMapper.
   * The first index of the list is determined by the "startIndex" variable
   */
  @GetMapping("/search/{value}/startIndex/{index}")
  public List<BookDTO> getGoogleBySearchAndIndex(@PathVariable("value") String value,
                                         @PathVariable("startIndex") int index) {
    return service.findFromGoogleWithIndex(value, index).stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }

  /**
   * Handles GET request for the mapping by search values.
   * @param value a value to search by in the Google Books API.
   * @param limit amount of results to display
   * @return a list of all Books mapped to DTO by BookMapper. The size of the returned list is limited by the "limit" value
   */
  @GetMapping("/search/{value}/limit/{maxResults}")
  public List<BookDTO> getGoogleBySearchWithLimit(@PathVariable("value") String value,
                                                  @PathVariable("maxResults") int limit) {
    return service.findFromGoogleWithLimit(value, limit).stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }

  /**
   * Handles GET request for the mapping by search values.
   * @param value a value to search by in the Google Books API.
   * @param index a value of the starting index to display
   * @param limit amount of results to display
   * @return a list of all Books mapped to DTO by BookMapper. The size of the returned list is limited by the "limit" value.
   * The first index of the list is determined by the "startIndex" variable
   */
  @GetMapping("/search/{value}/startIndex/{index}/limit/{maxResults}")
  public List<BookDTO> getGoogleBySearchWithStartingIndexAndLimit(@PathVariable("value") String value,
                                                               @PathVariable("startIndex") int index,
                                                               @PathVariable("maxResults") int limit) {
    return service.findFromGoogleWithIndexAndLimit(value, index, limit)
        .stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }
}
