package com.aprzybysz.library.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndustryIdentifier {

  private IndustryIdentifierTypeEnum type;
  private long identifier;
}
