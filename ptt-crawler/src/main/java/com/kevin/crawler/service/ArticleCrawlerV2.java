package com.kevin.crawler.service;

import com.kevin.crawler.model.Article;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleCrawlerV2 extends WebCrawler {
  private static final Logger log = LoggerFactory.getLogger(ArticleCrawlerV2.class);
  private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
    + "|png|mp3|mp4|zip|gz))$");
  private final PttArticleParser parser = new PttArticleParser();
  private final ArticleService articleService = new ArticleService();
  private final List<Article> list = new ArrayList<>();


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
    log.info("URL: {}", url);

    if (page.getParseData() instanceof HtmlParseData htmlParseData) {
      // 如果看板頁面則做解析當前文章link, 並且加入controller.seed
      String html = htmlParseData.getHtml();
      if (html.contains("r-ent")) {
        list.addAll(parser.parseArticlesV2(html));
        list.forEach(article -> this.myController.addSeed(article.getLink()));
        System.out.println(list);
      } else {
      // 如果是文章頁面則是解析全部內容，並且存到ＤＢ
        Article article = parser.parseArticle(html);
        articleService.save(article);
      }
    }
  }
}
