package com.kevin.crawler.repository.impl;


import com.kevin.crawler.model.Crawl;
import com.kevin.crawler.model.Crawl.CrawlBuilder;
import com.kevin.crawler.model.KeywordCondition;
import com.kevin.crawler.model.Subscriber;
import java.util.List;

class CrawlRepositoryTest {


  public static void main(String[] args) {
    CrawlRepository repository = new CrawlRepository();
    System.out.println(repository.getAllItem());


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
}
