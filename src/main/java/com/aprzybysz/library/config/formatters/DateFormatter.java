package com.aprzybysz.library.config.formatters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class DateFormatter implements Formatter<Long> {

  @Autowired
  private MessageSource messageSource;


  public DateFormatter() {
    super();
  }

  public Long parse(final String text, final Locale locale) throws ParseException {
    return LocalDate.parse(text).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public String print(final Long val, final Locale locale) {
    return LocalDate.from(Instant.ofEpochMilli(val)).toString();
  }
}
