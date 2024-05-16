package com.kevin.crawler.service;

import com.kevin.crawler.model.Keyword;
import com.kevin.crawler.model.KeywordCondition;
import com.kevin.crawler.repository.impl.KeywordRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeywordService {

  private static final Logger log = LoggerFactory.getLogger(KeywordService.class);
  private final KeywordRepository keywordRepository = new KeywordRepository();

  public void addKeyword(String userId, String board, String keyword) {
    Keyword key = getKeyword(board);
    boolean keywordExists = key.getKeywordConditions().stream()
      .anyMatch(getConditionPredicate(userId, keyword));
    if (keywordExists) {
      log.info("user: {} keyword already exists: {}", userId, keyword);
    } else {
      key.getKeywordConditions().add(new KeywordCondition(userId, keyword));
      key.setBoard(board);
      keywordRepository.saveItem(key);
      log.info("add finished...");
    }
  }

  public void removeKeyword(String userId, String board, String keyword) {
    Keyword key = getKeyword(board);
    boolean keywordExists = key.getKeywordConditions().stream()
      .anyMatch(getConditionPredicate(userId, keyword));
    if (keywordExists) {
      key.getKeywordConditions().removeIf(getConditionPredicate(userId, keyword));
      keywordRepository.saveItem(key);
      log.info("remove finished...");
    } else {
      log.info("user: {} keyword not exists: {}", userId, keyword);
    }
  }

  public Keyword getKeyword(String board) {
    Optional<Keyword> optionalKeyword = Optional.ofNullable(keywordRepository.getItemById(board));
    return optionalKeyword.orElseGet(() -> {
      Keyword keyword = new Keyword();
      keyword.setKeywordConditions(new ArrayList<>());
      return keyword;
    });
  }

  public List<String> getAllBoard() {
    return keywordRepository.getAllItem().stream().map(Keyword::getBoard).toList();
  }

  public List<Keyword> getAllKeyword() {
    return keywordRepository.getAllItem();
  }

  private @NotNull Predicate<KeywordCondition> getConditionPredicate(String userId, String keyword) {
    return keywordCondition ->
      keywordCondition.getKeyword().equals(keyword) && keywordCondition.getUserId()
        .equals(userId);
  }

  public String showKeywordList(String userId) {

    return null;
  }
}
