package com.kevin.crawler.model;

public class LineNotifyDto {

  private String lineId;
  private String title;
  private String link;

  public LineNotifyDto() {
  }

  public LineNotifyDto(String lineId, String title, String link) {
    this.lineId = lineId;
    this.title = title;
    this.link = link;
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
}
