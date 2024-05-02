package com.kevin.crawler.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Subscriber {

  private String name;

  // 1 - email 2 - sms
  private Integer notifyType;
  private String endpoint;

  public Subscriber() {
  }


  public Subscriber(String name, Integer notifyType, String endpoint) {
    this.name = name;
    this.notifyType = notifyType;
    this.endpoint = endpoint;
  }

  @Override
  public String toString() {
    return "Subscriber{" +
      "name='" + name + '\'' +
      ", notifyType=" + notifyType +
      ", endpoint='" + endpoint + '\'' +
      '}';
  }

  @DynamoDbPartitionKey
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getNotifyType() {
    return notifyType;
  }

  public void setNotifyType(Integer notifyType) {
    this.notifyType = notifyType;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }
}
