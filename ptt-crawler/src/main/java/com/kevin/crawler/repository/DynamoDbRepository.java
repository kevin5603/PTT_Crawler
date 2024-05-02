package com.kevin.crawler.repository;

import com.google.common.reflect.TypeToken;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

public abstract class DynamoDbRepository<T, R> extends DynamoDbEngine implements
  DynamoDbCrud<T, R> {

  private static final Logger log = LoggerFactory.getLogger(DynamoDbRepository.class);

  @Override
  public T getItemById(R key) {
    DynamoDbTable<T> table = getTable();
    Key partitionKey = switch (key) {
      case String s -> Key.builder().partitionValue(s).build();
      case Integer i -> Key.builder().partitionValue(i).build();
      default -> null;
    };
    return table.getItem(partitionKey);
  }

  @Override
  public T updateItem(T item) {
    DynamoDbTable<T> table = getTable();
    return table.updateItem(item);
  }

  @Override
  public T saveItem(T item) {
    DynamoDbTable<T> table = getTable();
    table.putItem(item);
    return item;
  }

  @Override
  public void deleteItem(R key) {
    DynamoDbTable<T> table = getTable();
    Key partitionKey = switch (key) {
      case String s -> Key.builder().partitionValue(s).build();
      case Integer i -> Key.builder().partitionValue(i).build();
      default -> null;
    };
    table.deleteItem(partitionKey);
  }

  @Override
  public List<T> getAllItem() {
    DynamoDbTable table = getTable();
    return table.scan().items().stream().toList();
  }

  @Override
  protected Class<T> getClazz() {
    return (Class<T>) new TypeToken<T>(getClass()) {}.getRawType();
  }
}
