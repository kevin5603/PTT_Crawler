package com.kevin.crawler.service;

import com.kevin.crawler.model.Article;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter.FilterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PttArticleParser {

  private static final Logger log = LoggerFactory.getLogger(PttArticleParser.class);
  private static final String URL_PREFIX = "https://www.ptt.cc";

  public Article parseArticle(String html) {
    String tag = "div.article-metaline span.article-meta-value";
    Document doc = Jsoup.parse(html);

    Article article = new Article();

    Elements pttArticle = doc.select("div#main-content");

    if (!pttArticle.select("div.article-metaline").isEmpty()) {
      String author = doc.select(tag).first().ownText().replaceAll("\\(.*?\\)", "").trim();
      String title = doc.select(tag).get(1).ownText();
      article.setAuthor(author);
      article.setTitle(title);
    }
    String content = parseContent(pttArticle);
    String link = doc.select("span.f2 a").attr("href");
    article.setLink(link);
    article.setContent(content);
    return article;
  }

  public List<Article> parseArticles(String html) {
    List<Article> list = new ArrayList<>();
    Document doc = Jsoup.parse(html);
    Elements elements = doc.select("div.r-ent");
    for (Element element : elements) {
      String link = URL_PREFIX + element.select("div.title a").attr("href");
      String author = element.select("div.meta div.author").text();
      String title = element.select("div.title a").text();
      list.add(new Article(link, author, title));
    }
    return list;
  }

  private @NotNull String parseContent(Elements select) {
    StringBuilder contentBuilder = new StringBuilder();
    select.filter((node, depth) -> {
      if (node.attr("id").contains("main-content") || depth != 1) {
        return FilterResult.CONTINUE;
      }
      if (node.attr("class").contains("article-meta")) {
        return FilterResult.SKIP_ENTIRELY;
      }
      if (node.attr("class").equals("f2") && ((Element)node).text().contains("※ 發信站: 批踢踢實業坊(ptt.cc)")) {
        return FilterResult.STOP;
      }
      String text = Jsoup.parse(node.toString()).text();
      contentBuilder.append(text).append(System.lineSeparator());
      return FilterResult.CONTINUE;
    });
    return contentBuilder.toString();
  }
}
