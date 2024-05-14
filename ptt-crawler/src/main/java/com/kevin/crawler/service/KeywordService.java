package com.kevin.crawler.service;

import com.kevin.crawler.model.Keyword;
import com.kevin.crawler.repository.impl.KeywordRepository;
import java.util.List;

public class KeywordService {

  private final KeywordRepository keywordRepository = new KeywordRepository();


  public void addKeyword(String board, String keyword) {

  }

  public void removeKeyword(String board, String keyword) {
  }

  public List<String> getAllBoard() {
    return keywordRepository.getAllItem().stream().map(Keyword::getBoard).toList();
  }

  public List<Keyword> getAllKeyword() {
    return keywordRepository.getAllItem();
  }
}
