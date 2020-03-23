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

@RestController
@RequestMapping("/api/rating")
public class RatingRestController {

  private BookService service;

  private AuthorRatingMapper mapper;

  public RatingRestController(BookService service, AuthorRatingMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public List<AuthorRatingDTO> get() {
    return service.getAuthorsRatings().entrySet()
        .stream()
        .map(mapper::ratingMapToAuthorRatingDTO)
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList());
  }
}
