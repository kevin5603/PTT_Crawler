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
  private final ArticleService articleService = new ArticleService();

  public Article parseArticle(String html) {
    Document doc = Jsoup.parse(html);
    String link = doc.select("link[rel=canonical]").attr("href");
    Article article = articleService.getArticleByLink(link);

    Elements pttArticle = doc.select("div#main-content");
    String content = parseContent(pttArticle);
    article.setLink(link);
    article.setContent(content);
    articleService.updateArticle(article);
    return article;
  }

  public List<Article> parseArticles(String html) {
    List<Article> list = new ArrayList<>();
    Document doc = Jsoup.parse(html);
    String board = doc.select("a.board").getFirst().ownText();
    Elements elements = doc.select("div.r-ent");
    for (Element element : elements) {
      String link = URL_PREFIX + element.select("div.title a").attr("href");
      String author = element.select("div.meta div.author").text();
      String title = element.select("div.title a").text();
      list.add(new Article(link, author, title, board));
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
