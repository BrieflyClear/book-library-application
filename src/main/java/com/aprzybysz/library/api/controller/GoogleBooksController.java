package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.api.dto.BookDTO;
import com.aprzybysz.library.api.mapper.BookMapper;
import com.aprzybysz.library.service.GoogleBooksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/google")
@AllArgsConstructor
public class GoogleBooksController {

  @Autowired
  private GoogleBooksService service;

  @Autowired
  private BookMapper mapper;

  @GetMapping("/search/{value}")
  public List<BookDTO> getGoogleBySearch(@PathVariable("value") String value) {
    return service.findFromGoogle(value, null).stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }

  @GetMapping("/search/{value}/limit/{maxResults}")
  public List<BookDTO> getGoogleBySearchWithLimit(@PathVariable("value") String value, @PathVariable("maxResults") int limit) {
    return service.findFromGoogle(value, limit).stream().map(mapper::bookToBookDTO).collect(Collectors.toList());
  }
}
