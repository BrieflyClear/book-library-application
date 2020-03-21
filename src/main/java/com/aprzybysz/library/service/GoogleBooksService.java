package com.aprzybysz.library.service;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.data.provider.DataProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GoogleBooksService {

  @Autowired
  private DataProvider dataProvider;

  public List<Book> findFromGoogle(String textToSearch) {
    return dataProvider.getBooksFromGoogle(textToSearch, null, null);
  }

  public List<Book> findFromGoogleWithLimit(String textToSearch, Integer limit) {
    return dataProvider.getBooksFromGoogle(textToSearch, null, limit);
  }

  public List<Book> findFromGoogleWithIndex(String textToSearch, Integer index) {
    return dataProvider.getBooksFromGoogle(textToSearch, index, null);
  }

  public List<Book> findFromGoogleWithIndexAndLimit(String textToSearch, Integer index, Integer limit) {
    return dataProvider.getBooksFromGoogle(textToSearch, index, limit);
  }
}
