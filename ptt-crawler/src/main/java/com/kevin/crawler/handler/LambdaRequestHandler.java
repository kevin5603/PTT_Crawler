package com.kevin.crawler.handler;

import static software.amazon.awssdk.auth.credentials.internal.CredentialSourceType.ENVIRONMENT;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.crawler.config.InitializeConfiguration;
import com.kevin.crawler.model.Crawl;
import com.kevin.crawler.model.KeywordCondition;
import com.kevin.crawler.model.line.LineRequest;
import com.kevin.crawler.model.line.LineRequestBody;
import com.kevin.crawler.model.line.dto.LineInfoDto;
import com.kevin.crawler.service.ArticleCrawler;
import com.kevin.crawler.service.CrawlService;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaRequestHandler implements RequestHandler<Map<String, Object>, String> {

  private static final Logger log = LoggerFactory.getLogger(LambdaRequestHandler.class);
  private final CrawlService crawlService = new CrawlService();

  public static void main(String[] args) {
    LambdaRequestHandler handler = new LambdaRequestHandler();
    Map<String, Object> map = Map.of("keyword", "車折抵券");

    handler.handleRequest(map, null);
  }

  @Override
  public String handleRequest(Map<String, Object> input, Context context) {
    long start = System.currentTimeMillis();
    try {
      log.info("爬蟲開始...");
      LambdaRequestHandler handler = new LambdaRequestHandler();
      List<Crawl> crawls = handler.crawlService.getCrawlList();

      ////////////// TODO 這區塊要在refactor
      if (input != null) {
        log.info("輸入值: {}", input);
        if (input.containsKey("keyword")) {
          String forceKeyword = (String) input.get("keyword");
          crawls.getFirst().getKeywordConditionList().add(new KeywordCondition(3, 2, forceKeyword));
          log.info("{}\r\n臨時增加關鍵字: {}", crawls, forceKeyword);
        }
      }

      LineInfoDto dto = new LineInfoDto();
      if (input != null) {
        if (input.containsKey("destination")) {
          ObjectMapper mapper = new ObjectMapper();
          mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
          mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

          LineRequestBody request = mapper.convertValue(input, LineRequestBody.class);
          dto = request.toDto();
          log.info("dto：{}", dto);
          String text = request.getEvents().getFirst().getMessage().getText();
          crawls.getFirst().getKeywordConditionList().add(new KeywordCondition(4, 2, text));
          log.info("{}\r\n從line傳來臨時增加的關鍵字: {}", crawls, text);
        }
      }
      LineInfoDto finalDto = dto;

      /////////////////

      CrawlController controller = handler.init(crawls);
      CrawlController.WebCrawlerFactory<ArticleCrawler> factory = () -> new ArticleCrawler(
        controller, crawls, finalDto);
      controller.start(factory, 1);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    long end = System.currentTimeMillis();

    log.info("爬蟲結束...耗時:{}", (end - start)/1000.0);
    return "爬蟲結束...";
  }

  private CrawlController init(List<Crawl> crawls) throws Exception {
    List<String> boards = crawls.stream().map(Crawl::getBoard).distinct().toList();

    InitializeConfiguration.initializeConfig();
    String crawlStorageFolder = "/tmp/data";
    CrawlConfig config = new CrawlConfig();
    config.setCrawlStorageFolder(crawlStorageFolder);
    config.setMaxDepthOfCrawling(0);
    config.setThreadShutdownDelaySeconds(0);
    config.setThreadMonitoringDelaySeconds(1);
    config.setCleanupDelaySeconds(0);

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
