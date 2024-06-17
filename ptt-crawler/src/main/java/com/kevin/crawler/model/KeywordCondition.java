package com.kevin.crawler.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class KeywordCondition {

  private String userId;
  // 1 - author 2 - subject 3 - content 4 - push
  private Integer pttConditionType;
  private String keyword;

  public KeywordCondition() {
  }

  public KeywordCondition(String userId, Integer pttConditionType, String keyword) {
    this.userId = userId;
    this.pttConditionType = pttConditionType;
    this.keyword = keyword;
  }

  // TODO currently only support query subject
  public KeywordCondition(String userId, String keyword) {
    this.keyword = keyword;
    this.pttConditionType = 2;
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "KeywordCondition{" +
      "userId=" + userId +
      ", pttConditionType=" + pttConditionType +
      ", keyword='" + keyword + '\'' +
      '}';
  }

  @DynamoDbPartitionKey
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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
