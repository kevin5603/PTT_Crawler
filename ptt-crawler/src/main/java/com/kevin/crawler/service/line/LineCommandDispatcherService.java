package com.kevin.crawler.service.line;

import com.kevin.crawler.model.LineConst;
import com.kevin.crawler.model.line.LineMessage;
import com.kevin.crawler.model.line.dto.LineInfoDto;
import com.kevin.crawler.service.KeywordService;
import com.linecorp.bot.messaging.model.TextMessage;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineCommandDispatcherService {
  private static final Logger log = LoggerFactory.getLogger(LineCommandDispatcherService.class);
  private final LineNotificationService lineNotificationService = new LineNotificationService();
  private final KeywordService keywordService = new KeywordService();

  public static void main(String[] args) {
    LineCommandDispatcherService l = new LineCommandDispatcherService();
    LineInfoDto dto = new LineInfoDto();
    dto.setMessage(new LineMessage("", "", "", "新增 joke 轉學2"));
    l.messageDispatcher(dto);
  }

  public void messageDispatcher(LineInfoDto dto) {
    String message = dto.getMessage().getText();
    String userId = dto.getUserId();
    Matcher addKeywordMatch = Pattern.compile(LineConst.ADD_KEYWORD_PATTERN).matcher(message);
    Matcher removeKeywordMatch = Pattern.compile(LineConst.REMOVE_KEYWORD_PATTERN).matcher(message);
    Matcher helpMatch = Pattern.compile(LineConst.HELP_KEYWORD_PATTERN).matcher(message);

    try {
      if (addKeywordMatch.matches()) {
        String board = addKeywordMatch.group(1);
        String keyword = addKeywordMatch.group(2);
        log.info("userId: {} Add keyword '{}' to board '{}'", userId, keyword, board);

        keywordService.addKeyword(userId, board, keyword);
        String response = String.format(" 看板%s 新增關鍵字成功：%s", board, keyword);
        sendReplyMessage(dto, response);
      } else if (removeKeywordMatch.matches()) {
        String board = addKeywordMatch.group(1);
        String keyword = addKeywordMatch.group(2);
        log.info("userId: {} Remove keyword '{}' from board '{}'", userId, keyword, board);

        keywordService.removeKeyword(userId, board, keyword);
        String response = String.format(" 看板%s 刪除關鍵字成功：%s", board, keyword);
        sendReplyMessage(dto, response);
      } else if (helpMatch.matches()) {
        log.info("help message");
        sendReplyMessage(dto, LineConst.HELP_MESSAGE);
      } else {
        log.info("user id: {} message: {}", userId, message);
        sendReplyMessage(dto, LineConst.NOT_MATCH_MESSAGE);
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
      String ERROR_MESSAGE = "Something went wrong, please try again later... ";
      sendReplyMessage(dto, ERROR_MESSAGE);
    }
  }

  private void sendReplyMessage(LineInfoDto dto, String message) {
    lineNotificationService.replyMessage(dto.getReplyToken(), List.of(new TextMessage(message)));
  }
}
