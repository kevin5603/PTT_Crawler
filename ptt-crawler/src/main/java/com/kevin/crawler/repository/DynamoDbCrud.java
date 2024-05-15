package com.kevin.crawler.repository;

import java.util.List;

public interface DynamoDbCrud<T, R> {

  public T saveItem(T item);

  public List<T> saveAllItem(List<T> items);

  public T updateItem(T item);

  public void deleteItem(R key);

  public T getItemById(R key);

  public List<T> getAllItem();

}
