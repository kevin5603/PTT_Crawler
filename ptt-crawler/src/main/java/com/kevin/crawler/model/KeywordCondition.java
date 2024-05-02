package com.kevin.crawler.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class KeywordCondition {

  private Integer id;
  // 1 - author 2 - subject 3 - content 4 - push
  private Integer pttConditionType;
  private String keyword;

  public KeywordCondition() {
  }

  public KeywordCondition(Integer id, Integer pttConditionType, String keyword) {
    this.id = id;
    this.pttConditionType = pttConditionType;
    this.keyword = keyword;
  }

  @Override
  public String toString() {
    return "KeywordCondition{" +
      "id=" + id +
      ", pttConditionType=" + pttConditionType +
      ", keyword='" + keyword + '\'' +
      '}';
  }

  @DynamoDbPartitionKey
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPttConditionType() {
    return pttConditionType;
  }

  public void setPttConditionType(Integer pttConditionType) {
    this.pttConditionType = pttConditionType;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
}
