package com.kevin.crawler.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class KeywordHistory {

  private String userIdAndLinkId;

  public KeywordHistory() {
  }

  public KeywordHistory(String userIdAndLinkId) {
    this.userIdAndLinkId = userIdAndLinkId;
  }

  @DynamoDbPartitionKey
  public String getUserIdAndLinkId() {
    return userIdAndLinkId;
  }

  public void setUserIdAndLinkId(String userIdAndLinkId) {
    this.userIdAndLinkId = userIdAndLinkId;
  }
}
