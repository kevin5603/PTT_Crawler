package com.kevin.crawler.repository.impl;

import com.kevin.crawler.model.Keyword;
import com.kevin.crawler.model.KeywordCondition;
import com.kevin.crawler.repository.DynamoDbRepository;
import java.util.List;
import java.util.Optional;

public class KeywordRepository extends DynamoDbRepository<Keyword, String> {


  public static void main(String[] args) {
    KeywordRepository k = new KeywordRepository();
////    Keyword item = k.getAllItem().getFirst();
//    List<KeywordCondition> list = List.of(new KeywordCondition("lineId", 1, "share"));
//    Keyword item = new Keyword("Ptt", list);
////    item.getKeywordConditions().add(new KeywordCondition("lineId2", 1, "go"));
//    k.saveItem(item);
    Optional<Keyword> aaaa = Optional.ofNullable(k.getItemById("Ptt"));
    System.out.println(aaaa);
    System.out.println(aaaa.isPresent());
    if (aaaa.isPresent()) {
      System.out.println(aaaa.get());
    }
  }

}
