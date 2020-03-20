package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.api.dto.BookDTO;
import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRestControllerTest {

  @LocalServerPort
  private int port;

  private String uri;

  @PostConstruct
  public void init() {
    uri = "http://localhost:" + port;
  }

  @MockBean
  BookService service;

  @Test
  public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredISBN_thenCorrect() {
    Book test = new Book.BookBuilder().isbn("9781592432172").title("title-test").create();
    when(service.findByIsbn("9781592432172")).thenReturn(Optional.of(test));

    get(uri + "/api/books/isbn/9781592432172").then().assertThat()
        .statusCode(HttpStatus.OK.value()).body("isbn", equalTo(test.getIsbn()))
        .body("title", equalTo(test.getTitle()));
  }

  @Test
  public void givenUrl_shouldReturn404OnWrongISBN_thenCorrect() {
    get(uri + "/api/books/isbn/0").then().assertThat().statusCode(404);
    get(uri + "/api/books/isbn/abcd").then().assertThat().statusCode(404);
    get(uri + "/api/books/isbn/").then().assertThat().statusCode(404);
    get(uri + "/api/books/isbn/+-*").then().assertThat().statusCode(404);
  }

  @Test
  public void givenUrl_booksShouldReturnNotEmptyList_thenCorrect() {
    Book test = new Book.BookBuilder().isbn("9781592432172").title("title-test").create();
    when(service.findAll()).thenReturn(List.of(test));

    var list = get(uri + "/api/books")
        .then().assertThat().statusCode(HttpStatus.OK.value()).extract().as(BookDTO[].class);
    assertEquals(1, list.length);
    assertEquals(test.getIsbn(), list[0].getIsbn());
    assertEquals(test.getTitle(), list[0].getTitle());
  }

  @Test
  public void givenUrl_shouldReturnEmptyListOnWrongCategory_thenCorrect() {
    get(uri + "/api/books/category/0").then().assertThat().body("results", hasSize(0));
    get(uri + "/api/books/category/*-++dsa:").then().assertThat().body("results", hasSize(0));
    get(uri + "/api/books/category/@").then().assertThat().body("results", hasSize(0));
    get(uri + "/api/books/category/aaaaaaaaaaaaaaaaaaaa").then().assertThat().body("results", hasSize(0));
  }

  @Test
  public void givenUrl_categoryComputersShouldReturnNotEmptyList_thenCorrect() {
    Book test = new Book.BookBuilder()
        .isbn("9781592432172")
        .title("title-test")
        .categories(new String[]{"Computers"})
        .create();
    when(service.findByCategory("Computers")).thenReturn(List.of(test));

    BookDTO[] list = get(uri + "/api/books/category/Computers")
        .then().assertThat().statusCode(HttpStatus.OK.value()).extract().as(BookDTO[].class);
    assertEquals(1, list.length);
    assertEquals(test.getIsbn(), list[0].getIsbn());
    assertEquals(test.getTitle(), list[0].getTitle());
  }
}