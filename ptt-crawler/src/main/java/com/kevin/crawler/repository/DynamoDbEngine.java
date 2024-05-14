package com.kevin.crawler.repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public abstract class DynamoDbEngine {

  private static DynamoDbClient ddb;
  private final DynamoDbEnhancedClient enhancedClient;

  public DynamoDbEngine() {
    ddb = DynamoDbClient.builder()
      .region(Region.US_WEST_2)
      .build();
    enhancedClient = DynamoDbEnhancedClient.builder()
      .dynamoDbClient(ddb).build();
  }

  protected DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
    return enhancedClient;
  }

  protected abstract Class getClazz();

  protected DynamoDbTable getTable() {
    return enhancedClient.table(getClazz().getSimpleName().toLowerCase(),
      TableSchema.fromBean(getClazz()));
  }

}
