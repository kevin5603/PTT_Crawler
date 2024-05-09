package com.kevin.crawler.service.line;

import com.kevin.crawler.model.LineConst;
import com.kevin.crawler.model.line.dto.LineInfoDto;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineCommandDispatcherService {

  private static final Logger log = LoggerFactory.getLogger(LineCommandDispatcherService.class);
  private LineNotificationService lineNotificationService = new LineNotificationService();

  public void messageDispatcher(LineInfoDto dto) {
    String message = dto.getMessage().getText();

    Matcher matcher = Pattern.compile(LineConst.ADD_KEYWORD_PATTERN).matcher(message);
    Matcher matcher2 = Pattern.compile(LineConst.REMOVE_KEYWORD_PATTERN).matcher(message);
    Matcher matcher3 = Pattern.compile(LineConst.HELP_KEYWORD_PATTERN).matcher(message);

    if (matcher.matches()) {
      String board = matcher.group();
      String keyword = matcher.group(1);
      // TODO 把關鍵字新增到db

      sendReplyMessage(dto, LineConst.ADD_KEYWORD_SUCCESS);
      log.info("userId: {} Add keyword '{}' to board '{}'", dto.getUserId(), keyword, board);
    } else if (matcher2.matches()) {
      String board = matcher.group();
      String keyword = matcher.group(1);
      // TODO 把關鍵字從db移除

      sendReplyMessage(dto, LineConst.REMOVE_KEYWORD_SUCCESS);
      log.info("userId: {} Remove keyword '{}' to board '{}'", dto.getUserId(), keyword, board);
    } else if (matcher3.matches()) {
      sendReplyMessage(dto, LineConst.HELP_MESSAGE);
    } else {
      sendReplyMessage(dto, LineConst.NOT_MATCH_MESSAGE);
    }
  }

  private void sendReplyMessage(LineInfoDto dto, String message) {
//    lineNotificationService.replyMessage(dto.getReplyToken(), List.of(new TextMessage(message)));
  }
}
