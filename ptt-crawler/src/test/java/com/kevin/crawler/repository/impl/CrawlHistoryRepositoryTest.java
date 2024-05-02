package com.kevin.crawler.repository.impl;

import com.kevin.crawler.model.CrawlHistory;
import org.junit.jupiter.api.Test;

class CrawlHistoryRepositoryTest {

  public static void main(String[] args) {
    CrawlHistoryRepository repository = new CrawlHistoryRepository();

    CrawlHistory history = new CrawlHistory(1, "ptt1");
    CrawlHistory history2 = new CrawlHistory(1, "ptt2");
    repository.saveItem(history);
    repository.saveItem(history2);
    System.out.println(repository.getAllItem());

    System.out.println(repository.existsByArticleLinkAndCrawlId("ptt1", 1));
    System.out.println(repository.existsByArticleLinkAndCrawlId("ptt1", 3));


    repository.deleteItem(1);
    repository.deleteItem(2);

//    Crawl crawl = CrawlBuilder.builder()
//      .id(3)
//      .board("LOL")
//      .subscriber(new Subscriber("kevin5603", 1, "fly911338@gmail.com"))
//      .keywordConditionList(
//        List.of((new KeywordCondition(1, 2, "go"))
//          , new KeywordCondition(2, 2, "share")))
//      .build();
////
//    repository.saveItem(crawl);
//
//    System.out.println(repository.getItemById(1));
//
//    System.out.println("=======");
//    crawl.setBoard("xxxxx");
//    System.out.println(crawl);
//    repository.updateItem(crawl);
//
//    crawl = repository.getItemById(1);
//    System.out.println(crawl);
//
//    repository.deleteItem(1);

  }

  @Test
  void findByArticleLinkAndCrawlId() {
  }
}
