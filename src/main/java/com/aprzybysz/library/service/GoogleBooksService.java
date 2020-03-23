package com.aprzybysz.library.service;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.data.provider.DataProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleBooksService {

  private DataProvider dataProvider;

  public GoogleBooksService(DataProvider dataProvider) {
    this.dataProvider = dataProvider;
  }

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
