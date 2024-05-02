package com.kevin.crawler.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Article {

  private String id;
  private String author;
  private String createdDate;
  private String title;
  private String link;
  private String content;

  public Article() {
  }

  public Article(String id, String author, String createdDate, String title, String link,
    String content) {
    this.id = id;
    this.author = author;
    this.createdDate = createdDate;
    this.title = title;
    this.link = link;
    this.content = content;
  }

  @Override
  public String toString() {
    return "Article{" +
      "id='" + id + '\'' +
      ", author='" + author + '\'' +
      ", createdDate='" + createdDate + '\'' +
      ", title='" + title + '\'' +
      ", link='" + link + '\'' +
      ", content='" + content + '\'' +
      '}';
  }

  @DynamoDbPartitionKey
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
