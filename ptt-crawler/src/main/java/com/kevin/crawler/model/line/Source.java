package com.kevin.crawler.model.line;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Source {
  @JsonProperty(value = "type")
  private String type;
  @JsonProperty(value = "userId")
  private String userId;

  public Source() {
  }

  public Source(String type, String userId) {
    this.type = type;
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Source{" +
      "type='" + type + '\'' +
      ", userId='" + userId + '\'' +
      '}';
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
