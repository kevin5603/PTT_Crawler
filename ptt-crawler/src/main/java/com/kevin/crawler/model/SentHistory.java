package com.kevin.crawler.model;

import java.util.Objects;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class SentHistory {

  private String userIdAndLinkId;

  public SentHistory() {
  }

  public SentHistory(String userIdAndLinkId) {
    this.userIdAndLinkId = userIdAndLinkId;
  }

  @DynamoDbPartitionKey
  public String getUserIdAndLinkId() {
    return userIdAndLinkId;
  }

  @Override
  public boolean equals(Object o) {
    return this == o || o.equals(this.userIdAndLinkId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(userIdAndLinkId);
  }

  public void setUserIdAndLinkId(String userIdAndLinkId) {
    this.userIdAndLinkId = userIdAndLinkId;
  }

  public static class SentHistoryDto {
    public String userId;
    public String linkId;
    public SentHistoryDto(SentHistory sentHistory) {
      String[] split = sentHistory.getUserIdAndLinkId().split("::");
      this.userId = split[0];
      this.linkId = split[1];
    }
  }


}
