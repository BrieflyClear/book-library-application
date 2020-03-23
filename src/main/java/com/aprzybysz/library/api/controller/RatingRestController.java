package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.api.dto.AuthorRatingDTO;
import com.aprzybysz.library.api.mapper.AuthorRatingMapper;
import com.aprzybysz.library.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls GET HTTP requests mapped by "/api/rating". Returns data from DataProvider's cache from external JSON files
 * @author Andrzej Przybysz
 */
@RestController
@RequestMapping("/api/rating")
public class RatingRestController {

  private BookService service;

  private AuthorRatingMapper mapper;

  public RatingRestController(BookService service, AuthorRatingMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  /**
   * Handles GET request for the main mapping
   * @return a list of all Authors' names with average rating of their books mapped to AuthorRatingDTO.
   * The list is sorted in descending order. The list does not contain records without any rating.
   */
  @GetMapping
  public List<AuthorRatingDTO> get() {
    return service.getAuthorsRatings().entrySet()
        .stream()
        .map(mapper::ratingMapToAuthorRatingDTO)
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList());
  }
}
