package com.aprzybysz.library.controller;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class WebController {

  @Autowired
  private BookService service;

  @ModelAttribute("allBooks")
  public List<Book> populateBooks() {
    return this.service.findAll();
  }

  @GetMapping({"/"})
  public String showWelcome() {
    return "index";
  }

  @GetMapping({"/api"})
  public String showDashboard() {
    return "api_instruction";
  }
}
