package com.aprzybysz.library.data;

import com.aprzybysz.library.data.model.Book;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

public class JsonParser {

  //public static final String JSON_FILE_REGEX = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9._-]+)+\\\\?";
  public static final String JSON_FILE_REGEX = "^(?:[\\w]\\:|\\\\)(\\\\[a-z_\\-\\s0-9\\.]+)+\\.json";
  private static JsonParser INSTANCE = null;
  private static Gson gson = null;
  private static String externalJsonFilePath = System.getProperty("user.dir") + "/books.json";

  private JsonParser() {
    gson = new Gson();
  }

  public static JsonParser getInstance() {
    if(INSTANCE == null) INSTANCE = new JsonParser();
    return INSTANCE;
  }

  public void setFileToRead(String file) {
    externalJsonFilePath = file;
  }

  public String getFileToRead() {
    return externalJsonFilePath;
  }

  public List<Book> readFromExternalJsonFile() {
    List<Book> books = new ArrayList<>();
    try(Reader reader = new FileReader(externalJsonFilePath)) {
      JsonObject root = gson.fromJson(reader, JsonObject.class);

      JsonArray jsonItemsArray = root.get("items").getAsJsonArray();
      jsonItemsArray.forEach(it -> books.add(extractBookFromJsonBookElement(it)));
    } catch(IOException e) {
      e.printStackTrace();
      // TODO add error page
    }
    return books;
  }

  public List<Book> parseJsonFile(String jsonBooks) {
    List<Book> books = new ArrayList<>();
    JsonObject root = gson.fromJson(jsonBooks, JsonObject.class);
    if(root.isJsonArray()) {
      root.getAsJsonArray().forEach(it -> books.add(extractBookFromJsonBookElement(it)));
    } else if(root.has("items")) {
      root.getAsJsonArray("items").forEach(it -> books.add(extractBookFromJsonBookElement(it)));
    } else {
      books.add(extractBookFromJsonBookElement(root));
    }
    return books;
  }

  private Book extractBookFromJsonBookElement(@NotNull JsonElement object) {
    String id = object.getAsJsonObject().get("id").getAsString();
    JsonObject volumeInfo = object.getAsJsonObject().get("volumeInfo").getAsJsonObject();
    String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : null;
    String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : null;
    String subtitle = volumeInfo.has("subtitle") ? volumeInfo.get("subtitle").getAsString() : null;
    String date = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : null;
    long publishedDateInUnix = getEpochMilliDate(date);
    String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : null;
    int pageCount = volumeInfo.has("pageCount") ? volumeInfo.get("pageCount").getAsInt() : 0;
    String isbn = "";
    if(volumeInfo.has("industryIdentifiers")) {
      isbn = getIsbn(volumeInfo.get("industryIdentifiers").getAsJsonArray());
      if(isbn == null) {
        isbn = id;
      }
    } else {
      isbn = id;
    }
    String thumbnailUrl = volumeInfo.get("imageLinks").getAsJsonObject().get("thumbnail").getAsString();
    String language = volumeInfo.get("language").getAsString();
    String previewLink = volumeInfo.get("previewLink").getAsString();
    double averageRating = volumeInfo.has("averageRating") ? volumeInfo.get("averageRating").getAsDouble() : 0;
    int ratingCount = volumeInfo.has("ratingsCount") ? volumeInfo.get("ratingsCount").getAsInt() : 0;
    ArrayList<String> authors = new ArrayList<>();
    if(volumeInfo.has("authors")) {
      volumeInfo.get("authors").getAsJsonArray().forEach(g -> authors.add(g.getAsString()));
    }
    ArrayList<String> categories = new ArrayList<>();
    if(volumeInfo.has("categories")) {
      volumeInfo.get("categories").getAsJsonArray().forEach(g -> categories.add(g.getAsString()));
    }
    String[] authorsArray = new String[authors.size()];
    authors.toArray(authorsArray);
    String[] categoriesArray = new String[categories.size()];
    categories.toArray(categoriesArray);
    return new Book(isbn, title, subtitle, publisher, publishedDateInUnix, description, pageCount,
        thumbnailUrl, language, previewLink, averageRating, ratingCount, authorsArray, categoriesArray);
  }

  private long getEpochMilliDate(String date) {
    DateTimeFormatter format = new DateTimeFormatterBuilder()
        .appendPattern("yyyy")
        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1).toFormatter();
    return date != null
        ? date.length() > 4
        ? LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        : LocalDate.parse(date, format).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        : 0L;
  }

  private String getIsbn(JsonArray isbnArray) {
    AtomicReference<String> isbnAtomic = new AtomicReference<>();
    isbnArray.forEach(g -> {
      if(g.getAsJsonObject().get("type").getAsString().equals("ISBN_13"))
        isbnAtomic.set(g.getAsJsonObject().get("identifier").getAsString());
    });
    if(isbnAtomic.get() != null) {
      return isbnAtomic.get();
    }
    else {
      return null;
    }
  }
}
