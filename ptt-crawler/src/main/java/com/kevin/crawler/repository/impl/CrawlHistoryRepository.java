package com.kevin.crawler.repository.impl;

import static software.amazon.awssdk.enhanced.dynamodb.internal.AttributeValues.numberValue;
import static software.amazon.awssdk.enhanced.dynamodb.internal.AttributeValues.stringValue;

import com.kevin.crawler.model.CrawlHistory;
import com.kevin.crawler.repository.DynamoDbRepository;
import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class CrawlHistoryRepository extends DynamoDbRepository<CrawlHistory, Integer> {

  public boolean existsByArticleLinkAndCrawlId(String articleLink, Integer crawlId) {
    DynamoDbTable table = getTable();
    Map<String, AttributeValue> expressionValues = Map.of(
      ":articleLink", stringValue(articleLink),
      ":crawlId", numberValue(crawlId));
    Expression expression = Expression.builder()
      .expression("crawl_id = :crawlId AND article_link = :articleLink")
      .expressionValues(expressionValues)
      .build();

    ScanEnhancedRequest request = ScanEnhancedRequest.builder()
      .consistentRead(true)
      .attributesToProject("article_link")
      .filterExpression(expression)
      .build();
    PageIterable scan = table.scan(request);
    return scan.items().stream().findAny().isPresent();
  }
}
