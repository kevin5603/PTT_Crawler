package com.kevin.crawler.model;

import java.util.List;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Keyword {
  private String board;
  private List<KeywordCondition> keywordConditions;

  public Keyword() {
  }

  public Keyword(String board, List<KeywordCondition> keywordConditions) {
    this.board = board;
    this.keywordConditions = keywordConditions;
  }

  @Override
  public String toString() {
    return "Keyword{" +
      "board='" + board + '\'' +
      ", keywordConditions=" + keywordConditions +
      '}';
  }

  @DynamoDbPartitionKey
  public String getBoard() {
    return board;
  }

  public void setBoard(String board) {
    this.board = board;
  }

  public List<KeywordCondition> getKeywordConditions() {
    return keywordConditions;
  }

  public void setKeywordConditions(
    List<KeywordCondition> keywordConditions) {
    this.keywordConditions = keywordConditions;
  }
}
