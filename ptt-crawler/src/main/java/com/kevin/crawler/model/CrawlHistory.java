package com.kevin.crawler.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class CrawlHistory {
  private Integer crawlId;
  private String articleLink;

  public CrawlHistory() {
  }

  public CrawlHistory(Integer crawlId, String articleLink) {
    this.crawlId = crawlId;
    this.articleLink = articleLink;
  }

  @Override
  public String toString() {
    return "CrawlHistory{" +
      ", crawlId=" + crawlId +
      ", ArticleLink=" + articleLink +
      '}';
  }

  @DynamoDbPartitionKey
  @DynamoDbAttribute("article_link")
  public String getArticleLink() {
    return articleLink;
  }

  public void setArticleLink(String articleLink) {
    this.articleLink = articleLink;
  }

  @DynamoDbAttribute("crawl_id")
  public Integer getCrawlId() {
    return crawlId;
  }

  public void setCrawlId(Integer crawlId) {
    this.crawlId = crawlId;
  }

}
