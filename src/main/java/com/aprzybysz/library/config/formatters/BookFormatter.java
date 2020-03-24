package com.aprzybysz.library.config.formatters;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class BookFormatter implements Formatter<Book> {

  @Autowired
  private BookService service;


  public BookFormatter() {
    super();
  }

  public Book parse(final String text, final Locale locale) throws ParseException {
    var bookOptional = service.findByIsbn(text);
    return bookOptional.orElse(null);
  }


  public String print(final Book object, final Locale locale) {
    return object.getIsbn();
  }
}
