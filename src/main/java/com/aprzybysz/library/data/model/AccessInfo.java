package com.aprzybysz.library.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessInfo {

  private String country;
  private /*ViewabilityEnum*/ String viewability;
  private boolean embeddable;
  private boolean publicDomain;
  private /*TextToSpeechPermissionEnum*/ String textToSpeechPermission;
  private Mode epub;
  private Mode pdf;
  private String webReaderLink;
  private /*AccessViewStatusEnum*/ String accessViewStatus;
  private boolean quoteSharingAllowed;

  @Data
  @AllArgsConstructor
  private class Mode {
    private boolean isAvailable;
    private String acsTokenLink;
  }
}
