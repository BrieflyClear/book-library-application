package com.aprzybysz.library.data;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

  @Test
  public void testMultipleFilesByRegex() {
    String file1 = "D:\\abcd.json";
    String file2 = "C:\\user\\abcd.json";
    String file3 = "abcd";
    String file4 = "abgd.xml";
    String file5 = "abgd.json";

    assertTrue(Pattern.matches(JsonParser.JSON_FILE_REGEX, file1));
    assertTrue(Pattern.matches(JsonParser.JSON_FILE_REGEX, file2));
    assertFalse(Pattern.matches(JsonParser.JSON_FILE_REGEX, file3));
    assertFalse(Pattern.matches(JsonParser.JSON_FILE_REGEX, file4));
    assertFalse(Pattern.matches(JsonParser.JSON_FILE_REGEX, file5));
  }

}