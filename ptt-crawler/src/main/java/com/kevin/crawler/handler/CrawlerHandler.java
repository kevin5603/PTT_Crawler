package com.kevin.crawler.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

/**
 * 排程: cron( * /10 * * * ? *)
 * 邏輯: 從db撈出看板資料，確認本次要撈取哪些看板
 * 使用爬蟲將指定看板第一頁的文章全部存取來，供後續查詢
 */
public class CrawlerHandler implements RequestHandler<Map<String, Object>, String> {

  @Override
  public String handleRequest(Map<String, Object> input, Context context) {
    return "Hello World !!";
  }
}
