package com.kevin.crawler.service;

import com.kevin.crawler.model.Article;
import com.kevin.crawler.model.Crawl;
import com.kevin.crawler.model.KeywordCondition;
import com.kevin.crawler.model.line.dto.LineInfoDto;
import com.kevin.crawler.service.aws.AwsNotificationHelper;
import com.kevin.crawler.service.line.LineNotificationService;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleCrawler extends WebCrawler {

  private static final Logger log = LoggerFactory.getLogger(ArticleCrawler.class);
  private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
    + "|png|mp3|mp4|zip|gz))$");
  private final CrawlHistoryService crawlHistoryService = new CrawlHistoryService();
  private final AwsNotificationHelper notificationHelper = new AwsNotificationHelper();
  private final List<Crawl> crawls;
  private final PttArticleParser parser = new PttArticleParser();
  Map<String, Set<Crawl>> keywordCrawl = new HashMap<>();
  LineNotificationService lineNotificationService = new LineNotificationService();
  private LineInfoDto dto;

  public ArticleCrawler(List<Crawl> crawls, LineInfoDto dto) {
    this.crawls = crawls;
    this.dto = dto;
  }

  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase();
    String PTT_INDEX = "https://www.ptt.cc/bbs/";
    return !FILTERS.matcher(href).matches()
      && href.startsWith(PTT_INDEX);
  }

  @Override
  public void visit(Page page) {
    String url = page.getWebURL().getURL();
    checkCurrentBoard(url);
    log.info("URL: {}", url);
    Map<String, List<Crawl>> crawlGroupByBoard = groupByCrawlList();

    if (page.getParseData() instanceof HtmlParseData htmlParseData) {
      String html = htmlParseData.getHtml();
      if (html.contains("r-ent")) {
        String pttBoardPattern = "^https://www.ptt.cc/bbs/(.*)/index.html$";
        Pattern p = Pattern.compile(pttBoardPattern);
        Matcher matcher = p.matcher(url);
        if (matcher.matches()) {
          List<Article> articles = parser.getArticles(html);

          String board = matcher.group(1);
          List<Crawl> crawlList = crawlGroupByBoard.get(board);

          for (Crawl crawl : crawlList) {
            List<KeywordCondition> keywordConditionList = crawl.getKeywordConditionList();

            for (KeywordCondition keywordCondition : keywordConditionList) {
              for (Article article : articles) {
                if (article.getTitle().toLowerCase()
                  .contains(keywordCondition.getKeyword().toLowerCase())) {
                  if (crawlHistoryService.checkExistCrawlHistory(article.getLink(), crawl.getId())) {
                    log.info("已發送過訊息... 文章連結:{}", article.getLink());
                    continue;
                  }
                  this.myController.addSeed(article.getLink());
                  Set<Crawl> orDefault = keywordCrawl.getOrDefault(article.getLink(),
                    new HashSet<>());
                  orDefault.add(crawl);
                  keywordCrawl.put(article.getLink(), orDefault);
                }
              }
            }
          }
        }
      } else {
        Article article = parser.parseArticle(html);
        log.info(article.toString());
        Set<Crawl> crawlSet = keywordCrawl.get(article.getLink());

        String message = """
        link: %s
        title: %s
        content: %s
        """.formatted(article.getLink(), article.getTitle(), article.getContent());
        log.info("傳送訊息內容: {}", message);
        lineNotificationService.notifyUser(dto, message);
        lineNotificationService.replyUser(dto, message);
        crawlHistoryService.saveAllItem(article.getLink(), crawlSet);
      }
    }
  }

  private void checkCurrentBoard(String url) {
    for (Crawl crawl : crawls) {
      if (url.contains(crawl.getBoard())) {
        break;
      }
    }
  }

  private Map<String, List<Crawl>> groupByCrawlList() {
    return crawls.stream().collect(Collectors.groupingBy(Crawl::getBoard));
  }

}
