package com.aprzybysz.library.api.controller;

import com.aprzybysz.library.api.dto.AuthorRatingDTO;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatingRestControllerTest {

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
  public void givenUrl_ratingsShouldReturnNotEmptyList_thenCorrect() {
    get(uri + "/api/rating").then().assertThat().body("results", hasSize((greaterThan(0))));
  }

  @Test
  public void givenUrl_ratingsShouldReturnRatingsGreaterThanZero_thenCorrect() {
    Map<String, Double> test = Map.of("Author Test", 3.87);
    when(service.getAuthorsRatings()).thenReturn(test);

    AuthorRatingDTO[] ratings = get(uri + "/api/rating")
        .then().statusCode(HttpStatus.OK.value()).extract().as(AuthorRatingDTO[].class);
    assertEquals("Author Test", ratings[0].getAuthor());
    assertEquals(3.87, ratings[0].getAverageRating());
    assertEquals(0L, Arrays.stream(ratings).filter(i -> i.getAverageRating() == 0.0).count());
  }
}