package com.kevin.crawler.model.line;

public class LineMessage {
  private String type;
  private String id;
  private String quoteToken;
  private String text;

  public LineMessage() {
  }

  public LineMessage(String type, String id, String quoteToken, String text) {
    this.type = type;
    this.id = id;
    this.quoteToken = quoteToken;
    this.text = text;
  }

  @Override
  public String toString() {
    return "MyMessage{" +
      "type='" + type + '\'' +
      ", id='" + id + '\'' +
      ", quoteToken='" + quoteToken + '\'' +
      ", text='" + text + '\'' +
      '}';
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getQuoteToken() {
    return quoteToken;
  }

  public void setQuoteToken(String quoteToken) {
    this.quoteToken = quoteToken;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
