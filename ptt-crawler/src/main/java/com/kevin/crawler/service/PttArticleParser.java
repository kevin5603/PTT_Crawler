package com.kevin.crawler.service;

import com.kevin.crawler.model.Article;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

public class PttArticleParser {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(PttArticleParser.class);

  private static final String URL_PREFIX = "https://www.ptt.cc";

  public List<Article> getArticles(String html) {
    List<Article> list = new ArrayList<>();
    Document doc = Jsoup.parse(html);
    Elements elements = doc.select("div.r-ent");
    if (!elements.isEmpty()) {
      for (Element element : elements) {
        Article article = new Article();
        String title = element.select("div.title a").text();
        String author = element.select("div.author").text();
        String createdDate = element.select("div.date").text();
        String link = element.select("div.title a").attr("href");
        article.setTitle(title);
        article.setAuthor(author);
        article.setCreatedDate(createdDate);
        article.setLink(URL_PREFIX + link);
        list.add(article);
      }
    }
    return list;
  }

  public Article parseArticle(String html) {
    String tag = "div.article-metaline span.article-meta-value";
    Document doc = Jsoup.parse(html);

    Article article = new Article();
    String content = doc.select("div#main-content").getFirst().ownText();
    article.setContent(content);
    String author = doc.select(tag).first().ownText().replaceAll("\\(.*?\\)", "").trim();
    String title = doc.select(tag).get(1).ownText();
    String createdDate = doc.select(tag).last().ownText();
    String link = doc.select("span.f2 a").attr("href");

    article.setAuthor(author);
    article.setTitle(title);
    article.setContent(content);
    article.setLink(link);
    log.info(createdDate);

    return article;
  }
}
