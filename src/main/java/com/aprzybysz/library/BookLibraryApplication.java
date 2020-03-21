package com.aprzybysz.library;

import com.aprzybysz.library.data.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Pattern;

@SpringBootApplication
public class BookLibraryApplication {

	public static void main(String[] args) {
		for(String arg : args) {
			if(Pattern.matches(JsonParser.JSON_FILE_REGEX, arg)) {
				JsonParser.getInstance().setFileToRead(arg);
			}
		}
		SpringApplication.run(BookLibraryApplication.class, args);
	}
}