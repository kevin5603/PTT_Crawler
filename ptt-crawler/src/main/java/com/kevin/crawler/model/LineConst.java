package com.kevin.crawler.model;

public class LineConst {
  public static final String HELP_KEYWORD_PATTERN = "^help$";
  public static final String ADD_KEYWORD_PATTERN = "^新增 ([A-Za-z]+) (.+)$";
  public static final String REMOVE_KEYWORD_PATTERN = "^刪除 ([A-Za-z]+) (.+)$";
  public static final String NOT_MATCH_MESSAGE = "無此指令，對指令有疑問請輸入help，查詢指令用法";
  public static final String HELP_MESSAGE = "新增 看板 關鍵字：新增追蹤關鍵字\n"
    + "刪除 看板 關鍵字：取消追蹤關鍵字\n"
    + "範例：新增 Lifeismoney goshare\n";
}
