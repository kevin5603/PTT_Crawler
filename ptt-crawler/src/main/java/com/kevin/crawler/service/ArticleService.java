package com.kevin.crawler.service;

import com.kevin.crawler.model.Article;
import com.kevin.crawler.model.LineNotifyDto;
import com.kevin.crawler.model.SentHistory;
import com.kevin.crawler.repository.impl.ArticleRepository;
import com.kevin.crawler.repository.impl.SentHistoryRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleService {

  private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
  private final ArticleRepository articleRepository = new ArticleRepository();
  private final SentHistoryRepository sentHistoryRepository = new SentHistoryRepository();

  public List<Article> getArticleByBoard(String board) {
    return this.articleRepository.getAllItem();
  }

  public void addHistory(List<LineNotifyDto> lineNotifyDtos) {
    lineNotifyDtos.forEach(dto -> {
      SentHistory sentHistory = new SentHistory(dto.getLineId() + "::" + dto.getLink());
      sentHistoryRepository.saveItem(sentHistory);
    });
  }

  public List<LineNotifyDto> filterBySentHistory(List<LineNotifyDto> lineNotifyDto) {
    List<SentHistory> allSentHistory = getAllSentHistory();
    return lineNotifyDto.stream()
      .filter(dto -> !allSentHistory.contains(dto.getLineId() + "::" + dto.getLink())).toList();
  }

  private List<SentHistory> getAllSentHistory() {
    return sentHistoryRepository.getAllItem();
  }

  public void save(Article article) {
    articleRepository.saveItem(article);
  }
}
