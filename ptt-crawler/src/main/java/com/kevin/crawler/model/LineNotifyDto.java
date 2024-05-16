package com.kevin.crawler.model;

public class LineNotifyDto {

  private String lineId;
  private String title;
  private String link;
  private String author;

  public LineNotifyDto() {
  }

  public LineNotifyDto(String lineId, String title, String link, String author) {
    this.lineId = lineId;
    this.title = title;
    this.link = link;
    this.author = author;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
