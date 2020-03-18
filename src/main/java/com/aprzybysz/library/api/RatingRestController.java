package com.aprzybysz.library.api;

import com.aprzybysz.library.api.dto.AuthorRatingDTO;
import com.aprzybysz.library.api.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/rating")
@AllArgsConstructor
public class RatingRestController {

  @Autowired
  private BookService service;

  @GetMapping
  public List<AuthorRatingDTO> get() {
    List<AuthorRatingDTO> rating = service.getAuthorsRatings();
    rating.sort(Collections.reverseOrder());
    return rating;
  }
}
