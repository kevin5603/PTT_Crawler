package com.kevin.crawler.model;

import java.util.List;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Crawl {
    private Integer id;
    private String board;
    private List<KeywordCondition> keywordConditionList;
    private Subscriber subscriber;

    public Crawl() {
    }

    public Crawl(Integer id, String board, List<KeywordCondition> keywordConditionList,
      Subscriber subscriber) {
        this.id = id;
        this.board = board;
        this.keywordConditionList = keywordConditionList;
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "Crawl{" +
          "id=" + id +
          ", board='" + board + '\'' +
          ", keywordConditionList=" + keywordConditionList +
          ", subscriber=" + subscriber +
          '}';
    }

    @DynamoDbPartitionKey
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public List<KeywordCondition> getKeywordConditionList() {
        return keywordConditionList;
    }

    public void setKeywordConditionList(
      List<KeywordCondition> keywordConditionList) {
        this.keywordConditionList = keywordConditionList;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public static class CrawlBuilder {

        private Integer id;
        private String board;
        private List<KeywordCondition> keywordConditionList;
        private Subscriber subscriber;

        public CrawlBuilder() {
        }

        public CrawlBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public CrawlBuilder board(String board) {
            this.board = board;
            return this;
        }

        public CrawlBuilder keywordConditionList(
          List<KeywordCondition> keywordConditionList) {
            this.keywordConditionList = keywordConditionList;
            return this;
        }

        public CrawlBuilder subscriber(Subscriber subscriber) {
            this.subscriber = subscriber;
            return this;
        }

        public Crawl build() {
            return new Crawl(id, board, keywordConditionList, subscriber);
        }

        public static CrawlBuilder builder() {
            return new CrawlBuilder();
        }
    }
}
