package com.kevin.crawler.service;

import com.kevin.crawler.model.Article;
import com.kevin.crawler.model.LineNotifyDto;
import com.kevin.crawler.model.KeywordHistory;
import com.kevin.crawler.repository.impl.ArticleRepository;
import com.kevin.crawler.repository.impl.KeywordHistoryRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleService {

  private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
  private final ArticleRepository articleRepository = new ArticleRepository();
  private final KeywordHistoryRepository keywordHistoryRepository = new KeywordHistoryRepository();

  public List<Article> getArticleByBoard(String board) {
    return this.articleRepository.getArticlesByBoard(board);
  }

  public void addHistory(List<LineNotifyDto> lineNotifyDtos) {
    lineNotifyDtos.forEach(dto -> {
      KeywordHistory keywordHistory = new KeywordHistory(dto.getLineId() + "::" + dto.getLink());
      keywordHistoryRepository.saveItem(keywordHistory);
    });
  }

  public List<LineNotifyDto> filterByKeywordHistory(List<LineNotifyDto> lineNotifyDto) {
    List<KeywordHistory> keywordHistoryList = getAllKeywordHistory();

    return lineNotifyDto.stream()
      .filter(dto -> !keywordHistoryList.stream().anyMatch(
        keywordHistory -> keywordHistory.getUserIdAndLinkId().equals(dto.getLineId() + "::" + dto.getLink()))).toList();
  }

  private List<KeywordHistory> getAllKeywordHistory() {
    return keywordHistoryRepository.getAllItem();
  }

  public void save(Article article) {
    articleRepository.saveItem(article);
  }

  public void saveAll(List<Article> articles) {
    articleRepository.saveAllItem(articles);
  }

  public Article getArticleByLink(String link) {
    return articleRepository.getArticleByLink(link);
  }

  public Article updateArticle(Article article) {
    return articleRepository.updateItem(article);
  }
}
