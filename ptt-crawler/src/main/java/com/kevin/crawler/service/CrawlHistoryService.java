package com.kevin.crawler.service;

import com.kevin.crawler.model.Crawl;
import com.kevin.crawler.model.CrawlHistory;
import com.kevin.crawler.repository.impl.CrawlHistoryRepository;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlHistoryService {

  private static final Logger log = LoggerFactory.getLogger(CrawlHistoryService.class);

  private final CrawlHistoryRepository crawlHistoryRepository = new CrawlHistoryRepository();

  public boolean checkExistCrawlHistory(String articleLink, Integer crawlId) {
    return crawlHistoryRepository.existsByArticleLinkAndCrawlId(articleLink, crawlId);
  }

  public void saveAllItem(String articleLink, Set<Crawl> crawlSet) {
    for (Crawl crawl : crawlSet) {
      CrawlHistory history = new CrawlHistory();
      history.setCrawlId(crawl.getId());
      history.setArticleLink(articleLink);
      crawlHistoryRepository.saveItem(history);
    }
  }
}
