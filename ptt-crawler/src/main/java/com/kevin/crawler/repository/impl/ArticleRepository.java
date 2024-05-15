package com.kevin.crawler.repository.impl;

import static software.amazon.awssdk.enhanced.dynamodb.internal.AttributeValues.stringValue;

import com.kevin.crawler.model.Article;
import com.kevin.crawler.repository.DynamoDbRepository;
import java.util.List;
import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class ArticleRepository extends DynamoDbRepository<Article, String> {

  public Article getArticleByLink(String link) {
    return getItemById(link);
  }

  public List<Article> getArticlesByBoard(String board) {
    DynamoDbTable<Article> table = getTable();

    Map<String, AttributeValue> expressionValues = Map.of(
      ":board", stringValue(board));
    ScanEnhancedRequest request = ScanEnhancedRequest.builder()
      .consistentRead(true)
      .attributesToProject("link", "author", "board", "title", "createdDate")
      .filterExpression(Expression.builder()
        .expression("board = :board")
        .expressionValues(expressionValues)
        .build())
      .build();
    return table.scan(request).items().stream().toList();


  }
}
