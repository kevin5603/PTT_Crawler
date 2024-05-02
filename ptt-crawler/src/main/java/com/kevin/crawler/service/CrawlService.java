package com.kevin.crawler.service;

import com.kevin.crawler.model.Crawl;
import com.kevin.crawler.repository.impl.CrawlRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlService {

  private static final Logger log = LoggerFactory.getLogger(CrawlService.class);

  private CrawlRepository crawlRepository = new CrawlRepository();

  public List<Crawl> getCrawlList() {
    return crawlRepository.getAllItem();
  }

  // get all board base on Crawl list
  public List<String> getBoardList() {
    List<String> list = getCrawlList().stream().map(Crawl::getBoard).distinct().toList();
    log.info("List of boards: {}", list);
    return list;
  }

}
