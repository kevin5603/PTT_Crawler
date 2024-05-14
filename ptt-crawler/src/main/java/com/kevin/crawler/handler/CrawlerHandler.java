package com.kevin.crawler.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.kevin.crawler.service.line.LineNotificationService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排程: cron( * /10 * * * ? *) 邏輯: 從db撈出看板資料，確認本次要撈取哪些看板 使用爬蟲將指定看板第一頁的文章全部存取來，供後續查詢
 */
public class CrawlerHandler implements RequestHandler<Map<String, Object>, Void> {

  private static final Logger log = LoggerFactory.getLogger(CrawlerHandler.class);
  private final LineNotificationService lineNotificationService = new LineNotificationService();

  @Override
  public Void handleRequest(Map<String, Object> input, Context context) {
    // 把關鍵字表撈出來
    List keywordList = getKeywordList();
    // 將關鍵字list轉乘看板list存起來攻下面使用

    // 利用上面的看版list把符合條件的看板文章也撈出來
    // 依序查詢是否有命中關鍵字，如果有則存到一個待發送物件(裡面應該要有lineId, ptt link, title)list
    // TODO 依序呼叫line service發送訊息
    notifyUser(null);

    return null;
  }

  // TODO
  private List getKeywordList() {
    return null;
  }

  // TODO
  private void notifyUser(List list) {
    for (Object o :list) {
      lineNotificationService.pushMessage(null, null);
    }
  }
}
