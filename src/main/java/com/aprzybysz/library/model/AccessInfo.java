package com.aprzybysz.library.model;

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


  private class Mode {
    private boolean isAvailable;
    private String acsTokenLink;
  }
}
