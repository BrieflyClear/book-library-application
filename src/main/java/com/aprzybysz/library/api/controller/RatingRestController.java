package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.api.dto.AuthorRatingDTO;
import com.aprzybysz.library.api.mapper.AuthorRatingMapper;
import com.aprzybysz.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rating")
@CrossOrigin
@AllArgsConstructor
public class RatingRestController {

  @Autowired
  private BookService service;

  @Autowired
  private AuthorRatingMapper mapper;

  @GetMapping
  public List<AuthorRatingDTO> get() {
    return service.getAuthorsRatings().entrySet()
        .stream()
        .map(mapper::ratingMapToAuthorRatingDTO)
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList());
  }
}
