package com.kevin.crawler.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.kevin.crawler.service.ArticleCrawler;
import com.kevin.crawler.service.KeywordService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排程: cron( * /10 * * * ? *) 邏輯: 從db撈出看板資料，確認本次要撈取哪些看板 使用爬蟲將指定看板第一頁的文章全部存取來，供後續查詢
 */
public class CrawlerHandler implements
  RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final Logger log = LoggerFactory.getLogger(CrawlerHandler.class);
  private final KeywordService keywordService = new KeywordService();

  @Override
  public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent request,
    final Context context) {
    long start = System.currentTimeMillis();
    APIGatewayProxyResponseEvent response;
    try {
      List<String> boards = keywordService.getAllBoard();
      CrawlController controller = this.initCrawlConfig(boards);
      CrawlController.WebCrawlerFactory<ArticleCrawler> factory = ArticleCrawler::new;
      controller.start(factory, 1);
      controller.shutdown();
      controller.waitUntilFinish();
      response = createAPIGatewayResponse(200, "ptt crawler finish...");
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
      response = createAPIGatewayResponse(500, e.getMessage());
    }
    long end = System.currentTimeMillis();
    log.info("爬蟲結束...耗時: {}秒", (end - start) / 1000.0);
    return response;
  }

  private CrawlController initCrawlConfig(List<String> boards) throws Exception {
    String crawlStorageFolder = "/tmp/data";
    CrawlConfig config = new CrawlConfig();
    config.setCrawlStorageFolder(crawlStorageFolder);
    config.setMaxDepthOfCrawling(0);
    config.setThreadShutdownDelaySeconds(2);
    config.setThreadMonitoringDelaySeconds(2);
    config.setCleanupDelaySeconds(2);

    // Some boards on PTT have an 18+ restriction and require setting cookies to access.
    Collection<BasicHeader> defaultHeaders = config.getDefaultHeaders();
    defaultHeaders.add(new BasicHeader("cookie", "over18=1;"));
    config.setDefaultHeaders(defaultHeaders);

    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

    for (String board : boards) {
      controller.addSeed(String.format("https://www.ptt.cc/bbs/%s/index.html", board));
      log.info("crawl board: {}", board);
    }
    return controller;
  }

  private APIGatewayProxyResponseEvent createAPIGatewayResponse(Integer statusCode, String body) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("X-Custom-Header", "application/json");
    return new APIGatewayProxyResponseEvent()
      .withHeaders(headers);
  }

}
