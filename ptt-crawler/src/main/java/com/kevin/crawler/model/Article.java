package com.kevin.crawler.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Article {

  private String link;
  private String author;
  private String createdDate;
  private String title;
  private String content;
  private String board;

  public Article() {
  }

  public Article(String author, String createdDate, String title, String link,
    String content, String board) {
    this.link = link;
    this.author = author;
    this.createdDate = createdDate;
    this.title = title;
    this.content = content;
    this.board = board;
  }

  @Override
  public String toString() {
    return "Article{" +
      "link='" + link + '\'' +
      ", author='" + author + '\'' +
      ", createdDate='" + createdDate + '\'' +
      ", title='" + title + '\'' +
      ", content='" + content + '\'' +
      ", board='" + board + '\'' +
      '}';
  }

  @DynamoDbPartitionKey
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getBoard() {
    return board;
  }

  public void setBoard(String board) {
    this.board = board;
  }
}
