package com.kevin.crawler.service;

import com.kevin.crawler.model.Article;
import com.kevin.crawler.repository.impl.ArticleRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleService {

  private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
  private final ArticleRepository articleRepository = new ArticleRepository();

  public List<Article> getArticleByBoard(String board) {
    List<Article> list = this.articleRepository.getAllItem().stream()
      .filter(article -> article.getBoard().equals(board)).toList();
    return list;
  }
}
