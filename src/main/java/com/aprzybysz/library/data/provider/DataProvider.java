package com.aprzybysz.library.data.provider;

import com.aprzybysz.library.data.model.Book;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Singleton class with static factory method and lazy loading
 */

@Component
public class DataProvider {

  static org.apache.logging.log4j.Logger logger = LogManager.getLogger(DataProvider.class);

  public static final String JSON_FILE_REGEX = "^(?:[\\w]\\:|\\\\)(\\\\[a-z_\\-\\s0-9\\.]+)+\\.json";
  public static final String GOOGLE_API_KEY = "AIzaSyDWESzsUyAtujHwVuXz8eqpa6OT8tZvFVo";
  public static final String GOOGLE_API_URI = "https://www.googleapis.com/books/v1/volumes";
  private String externalJsonFilePath = System.getProperty("user.dir") + "/misc/books.json";
  private List<Book> cacheBookList = new ArrayList<>();
  private Gson gson;

  private static DataProvider instance;

  private DataProvider() {
    gson = new Gson();
  }

  public static DataProvider getInstance() {
    if(instance == null) {
      instance = new DataProvider();
    }
    return instance;
  }

  public List<Book> getCacheBookList() {
    return cacheBookList;
  }

  public void setFileToRead(String file) {
    externalJsonFilePath = file;
    cacheBookList.clear();
  }

  public String getFileToRead() {
    return externalJsonFilePath;
  }

  public List<Book> getAllBooks() {
    if(cacheBookList.isEmpty()) {
      cacheBookList = readFromExternalJsonFile();
    }
    return cacheBookList;
  }

  private List<Book> readFromExternalJsonFile() {
    List<Book> books = new ArrayList<>();
    try(Reader reader = new FileReader(externalJsonFilePath)) {
      JsonObject root = gson.fromJson(reader, JsonObject.class);
      JsonArray jsonItemsArray = root.get("items").getAsJsonArray();
      jsonItemsArray.forEach(it -> books.add(extractBookFromJsonBookElement(it)));
    } catch(IOException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      // TODO add error page
    }
    return books;
  }

  private Book extractBookFromJsonBookElement(@NotNull JsonElement object) {
    String id = object.getAsJsonObject().get("id").getAsString();
    JsonObject volumeInfo = object.getAsJsonObject().get("volumeInfo").getAsJsonObject();
    String title = getValueFromJsonObject("title", volumeInfo, String.class);
    String publisher = getValueFromJsonObject("publisher", volumeInfo, String.class);
    String subtitle = getValueFromJsonObject("subtitle", volumeInfo, String.class);
    String date = getValueFromJsonObject("publishedDate", volumeInfo, String.class);
    Long publishedDateInUnix = getEpochMilliDate(date);
    String description = getValueFromJsonObject("description", volumeInfo, String.class);
    Integer pageCount = getValueFromJsonObject("pageCount", volumeInfo, Integer.class);
    String isbn = "";
    if(volumeInfo.has("industryIdentifiers")) {
      isbn = getIsbn(volumeInfo.get("industryIdentifiers").getAsJsonArray());
      if(isbn == null) {
        isbn = id;
      }
    } else {
      isbn = id;
    }
    String thumbnailUrl = volumeInfo.has("imageLinks")
        ? getValueFromJsonObject("thumbnail", volumeInfo.get("imageLinks").getAsJsonObject(), String.class)
        : null;
    String language = getValueFromJsonObject("language", volumeInfo, String.class);
    String previewLink = getValueFromJsonObject("previewLink", volumeInfo, String.class);
    Double averageRating = getValueFromJsonObject("averageRating", volumeInfo, Double.class);
    Integer ratingCount = getValueFromJsonObject("ratingCount", volumeInfo, Integer.class);
    ArrayList<String> authors = new ArrayList<>();
    if(volumeInfo.has("authors")) {
      volumeInfo.get("authors").getAsJsonArray().forEach(g -> authors.add(g.getAsString()));
    }
    ArrayList<String> categories = new ArrayList<>();
    if(volumeInfo.has("categories")) {
      volumeInfo.get("categories").getAsJsonArray().forEach(g -> categories.add(g.getAsString()));
    }
    String[] authorsArray = null;
    if(!authors.isEmpty()) {
      authorsArray = new String[authors.size()];
      authors.toArray(authorsArray);
    }
    String[] categoriesArray = null;
    if(!categories.isEmpty()) {
      categoriesArray = new String[categories.size()];
      categories.toArray(categoriesArray);
    }
    return new Book.BookBuilder()
        .isbn(isbn)
        .title(title)
        .subtitle(subtitle)
        .publisher(publisher)
        .publishedDate(publishedDateInUnix)
        .description(description)
        .pageCount(pageCount)
        .thumbnailUrl(thumbnailUrl)
        .language(language)
        .averageRating(averageRating)
        .ratingsCount(ratingCount)
        .previewLink(previewLink)
        .authors(authorsArray)
        .categories(categoriesArray)
        .create();
  }

  private <T> T getValueFromJsonObject(String valueToGet, JsonObject parentObject, Class<T> classT) {
    JsonElement element = parentObject.has(valueToGet) ? parentObject.get(valueToGet) : null;
    if(element != null) {
      return gson.fromJson(element, classT);
    } else {
      return null;
    }
  }

  private Long getEpochMilliDate(String date) {
    DateTimeFormatter format = new DateTimeFormatterBuilder()
        .appendPattern("yyyy")
        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
        .toFormatter();
    if(date != null) {
      if(date.length() > 4) {
        return LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
      } else {
        return LocalDate.parse(date, format).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
      }
    } else {
      return null;
    }
  }

  private String getIsbn(JsonArray isbnArray) {
    AtomicReference<String> isbnAtomic = new AtomicReference<>();
    isbnArray.forEach(g -> {
      if(g.getAsJsonObject().get("type").getAsString().equals("ISBN_13"))
        isbnAtomic.set(g.getAsJsonObject().get("identifier").getAsString());
    });
    if(isbnAtomic.get() != null) {
      return isbnAtomic.get();
    } else {
      return null;
    }
  }

  public List<Book> getBooksFromGoogle(String search, Integer startingIndex, Integer limit) {
    RestTemplate template = new RestTemplateBuilder().build();
    String requestURL = GOOGLE_API_URI + "?key=" + GOOGLE_API_KEY + "&q=" + search;
    if(limit != null && limit > 0) {
      requestURL = requestURL + "&maxResults=" + limit;
    }
    if(startingIndex != null && startingIndex > 0) {
      requestURL = requestURL + "&startingIndex=" + startingIndex;
    }
    var string = template.getForObject(requestURL, String.class);
    var json = gson.fromJson(string, JsonObject.class);
    return parseJsonRootObject(json);
  }

  private List<Book> parseJsonRootObject(JsonObject root) {
    List<Book> books = new ArrayList<>();
    if(root.isJsonArray()) {
      root.getAsJsonArray().forEach(it -> books.add(extractBookFromJsonBookElement(it)));
    } else if(root.getAsJsonObject().has("items")) {
      root.getAsJsonObject().getAsJsonArray("items").forEach(it -> books.add(extractBookFromJsonBookElement(it)));
    } else {
      books.add(extractBookFromJsonBookElement(root));
    }
    return books;
  }
}
