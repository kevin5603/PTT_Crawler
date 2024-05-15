package com.kevin.crawler.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kevin.crawler.model.Article;
import com.kevin.crawler.model.Keyword;
import com.kevin.crawler.model.KeywordCondition;
import com.kevin.crawler.model.LineNotifyDto;
import com.kevin.crawler.service.ArticleService;
import com.kevin.crawler.service.KeywordService;
import com.kevin.crawler.service.line.LineNotificationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排程: cron( * /10 * * * ? *) 邏輯:從db撈資料已儲存的文章資料，跟關鍵字這張表的資料做比對，若有則發送line給使用者。
 */
public class ArticleMatcherHandler implements RequestHandler<Map<String, Object>, Void> {

  private static final Logger log = LoggerFactory.getLogger(ArticleMatcherHandler.class);
  private final LineNotificationService lineNotificationService = new LineNotificationService();
  private final KeywordService keywordService = new KeywordService();
  private final ArticleService articleService = new ArticleService();

  /**
   * @param input   The Lambda Function input
   * @param context The Lambda execution environment context object.
   * @return
   */
  @Override
  public Void handleRequest(Map<String, Object> input, Context context) {
    long start = System.currentTimeMillis();
    // 把關鍵字表撈出來
    List<Keyword> keywordList = keywordService.getAllKeyword();
    // 將關鍵字list轉乘看板list存起來攻下面使用
    List<String> boards = keywordService.getAllBoard();
    Map<String, List<Keyword>> groupByBoard = keywordList.stream()
      .collect(Collectors.groupingBy(Keyword::getBoard));

    // 利用上面的看版list把符合條件的看板文章也撈出來
    List<LineNotifyDto> lineNotifyDtos = getLineNotifyDtos(boards, groupByBoard);
    lineNotifyDtos = articleService.filterBySentHistory(lineNotifyDtos);

    if (!lineNotifyDtos.isEmpty()) {
      log.info("發現有符合關鍵字的文章，發送訊息給使用者");
      addToHistory(lineNotifyDtos);
      // 依序查詢是否有命中關鍵字，如果有則存到一個待發送物件(裡面應該要有lineId, ptt link, title)list
      notifyUser(lineNotifyDtos);
    }

    long end = System.currentTimeMillis();
    log.info("關鍵字查詢結束...耗時: {}秒", (end - start) / 1000.0);
    return null;
  }

  private void addToHistory(List<LineNotifyDto> lineNotifyDtos) {
    articleService.addHistory(lineNotifyDtos);

  }

  private @NotNull List<LineNotifyDto> getLineNotifyDtos(List<String> boards,
    Map<String, List<Keyword>> groupByBoard) {
    List<LineNotifyDto> lineNotifyDtos = new ArrayList<>();
    for (String board : boards) {
      if (groupByBoard.containsKey(board)) {
        List<Article> articleByBoard = articleService.getArticleByBoard(board);
        articleByBoard.forEach(article -> {
          var title = article.getTitle().toLowerCase();
          List<KeywordCondition> keywordConditions = groupByBoard.get(board).getFirst()
            .getKeywordConditions();
          keywordConditions.forEach(keywordCondition -> {
            if (title.contains(keywordCondition.getKeyword().toLowerCase())) {
              lineNotifyDtos.add(new LineNotifyDto(keywordCondition.getUserId(), article.getTitle(),
                article.getLink()));
            }
          });
        });
      }
    }
    return lineNotifyDtos;
  }

  private void notifyUser(List<LineNotifyDto> list) {
    Map<String, String> map = list.stream()
      .collect(Collectors.toMap(
        LineNotifyDto::getLineId, l -> String.format("%s\r\n%s", l.getTitle(), l.getLink()),
        (a, b) -> String.join("\r\n=========\r\n", a, b)
      ));

    map.forEach(lineNotificationService::pushMessage);
  }
}
