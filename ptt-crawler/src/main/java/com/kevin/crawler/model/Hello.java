package com.kevin.crawler.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Hello {
  private String lineId;
  private String name;

  public Hello(String lineId, String name) {
    this.lineId = lineId;
    this.name = name;
  }

  public Hello() {
  }

  @Override
  public String toString() {
    return "Hello{" +
      "lineId='" + lineId + '\'' +
      ", name='" + name + '\'' +
      '}';
  }

  @DynamoDbPartitionKey
  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
