package com.kevin.crawler.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kevin.crawler.config.InitializeConfiguration;
import com.kevin.crawler.service.ArticleCrawlerV2;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排程：每分鐘查一次 邏輯:從db撈資料已儲存的文章資料，跟關鍵字這張表的資料做比對，若有則發送line給使用者。
 */
public class ArticleMatcherHandler implements RequestHandler<Map<String, Object>, String> {

  private static final Logger log = LoggerFactory.getLogger(ArticleMatcherHandler.class);
  // boardService

  public static void main(String[] args) {
    ArticleMatcherHandler handler = new ArticleMatcherHandler();
    Map<String, Object> map = Map.of("keyword", "車折抵券");

    handler.handleRequest(map, null);
  }

  /**
   * @param input   The Lambda Function input
   * @param context The Lambda execution environment context object.
   * @return
   */
  @Override
  public String handleRequest(Map<String, Object> input, Context context) {
    long start = System.currentTimeMillis();
    try {
      // TODO
      // List<String> boards = boardService.findScanBoard();  // ['Movie', 'Joke', 'Stock']
      List<String> boards = null;
      boards = List.of("Lifeismoney");

      CrawlController controller = this.init(boards);
      CrawlController.WebCrawlerFactory<ArticleCrawlerV2> factory = ArticleCrawlerV2::new;
      controller.start(factory, 1);
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
    }
    long end = System.currentTimeMillis();
    log.info("爬蟲結束...耗時: {}秒", (end - start) / 1000.0);
    return "爬蟲結束...";
  }

  private CrawlController init(List<String> boards) throws Exception {
    InitializeConfiguration.initializeConfig();
    String crawlStorageFolder = "/tmp/data";
    CrawlConfig config = new CrawlConfig();
    config.setCrawlStorageFolder(crawlStorageFolder);
    config.setMaxDepthOfCrawling(0);
    config.setThreadShutdownDelaySeconds(5);
    config.setThreadMonitoringDelaySeconds(5);
    config.setCleanupDelaySeconds(5);

    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

    for (String board : boards) {
      controller.addSeed(String.format("https://www.ptt.cc/bbs/%s/index.html", board));
    }

    return controller;
  }
}
