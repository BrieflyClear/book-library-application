package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.service.GoogleBooksService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoogleBooksControllerTest {

  @LocalServerPort
  private int port;

  private String uri;

  @PostConstruct
  public void init() {
    uri = "http://localhost:" + port;
  }

  @MockBean
  GoogleBooksService service;

  private static List<Book> list;

  @BeforeAll
  static void initArray() {
    list = new ArrayList<>();
    list.add(new Book.BookBuilder().isbn("9781592432171").title("AI").create());
    list.add(new Book.BookBuilder().isbn("9781592432172").title("Microservices").create());
    list.add(new Book.BookBuilder().isbn("9781592432173").title("Clean code").create());
    list.add(new Book.BookBuilder().isbn("9781592432174").title("C++ for dummies").create());
    list.add(new Book.BookBuilder().isbn("9781592432175").title("Mockito guide").create());
    list.add(new Book.BookBuilder().isbn("9781592432176").title("Java start").create());
  }

  @Test
  public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredParams_thenCorrect() {
    when(service.findFromGoogle("C++")).thenReturn(List.of(list.get(3)));
    when(service.findFromGoogle("9781592432176")).thenReturn(List.of(list.get(5)));

    get(uri + "/api/google/search/9781592432176").then().assertThat()
        .statusCode(HttpStatus.OK.value()).body("isbn", equalTo(list.get(5).getIsbn()))
        .body("title", equalTo(list.get(5).getTitle()));

    get(uri + "/api/google/search/C++").then().assertThat()
        .statusCode(HttpStatus.OK.value()).body("isbn", equalTo(list.get(3).getIsbn()))
        .body("title", equalTo(list.get(3).getTitle()));
  }

  @Test
  public void givenUrl_shouldReturn404OnWrongLimitValue_thenCorrect() {
    get(uri + "/api/google/search/C++/limit/0").then().assertThat().statusCode(404);
    get(uri + "/api/google/search/C++/limit/-10").then().assertThat().statusCode(404);
  }
}