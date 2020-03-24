package com.aprzybysz.library.controller;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class WebController {

  @Autowired
  private BookService service;

  @ModelAttribute("allBooks")
  public List<Book> populateBooks() {
    return this.service.findAll();
  }

  @ModelAttribute("allCategories")
  public List<String> populateCategories() {
    var categories = new ArrayList<String>();
    categories.add("All books");
    categories.add("No category");
    var list = service.getCategories();
    list.sort(Comparator.naturalOrder());
    categories.addAll(list);
    return categories;
  }

  @GetMapping({"/", "/books"})
  public String showWelcome(@RequestParam(required = false) String category, Model model) {
    if(category == null) {
      category = "All books";
    }
    if(category.equals("All books")) {
      model.addAttribute("books", populateBooks());
    } else if(category.equals("No category")) {
      model.addAttribute("books", service.findByCategory(null));
    } else {
      model.addAttribute("books", service.findByCategory(category));
    }
    model.addAttribute("category", category);
    return "index";
  }

  @GetMapping({"/api"})
  public String showDashboard() {
    return "api_instruction";
  }

  @GetMapping("/favicon.ico")
  @ResponseBody
  void returnNoFavicon() {
  }

  @GetMapping("/book")
  public String showBookDetails(@RequestParam("id") String isbn, Model model) {
    model.addAttribute("book", service.findByIsbn(isbn).orElse(null));
    return "book_details";
  }
}
