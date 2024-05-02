package com.kevin.crawler.service.line;

import com.kevin.crawler.model.line.dto.LineInfoDto;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.client.MessagingApiClientException;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.MulticastRequest;
import com.linecorp.bot.messaging.model.PushMessageRequest;
import com.linecorp.bot.messaging.model.ReplyMessageRequest;
import com.linecorp.bot.messaging.model.TextMessage;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineNotificationService {

  private static final Logger log = LoggerFactory.getLogger(LineNotificationService.class);
  private static final String CHANNEL_TOKEN = System.getenv("CHANNEL_TOKEN");
  private static final MessagingApiClient CLIENT = MessagingApiClient.builder(CHANNEL_TOKEN).build();

  /**
   * Line - Push message: One-to-one
   * @param lineUserId
   * @param message
   */
  public void pushMessage(String lineUserId, String message) {
    TextMessage textMessage = new TextMessage(message);
    PushMessageRequest request = new PushMessageRequest.Builder(lineUserId,
      List.of(textMessage)).build();
    try {
      CLIENT.pushMessage(UUID.randomUUID(), request).get();
    } catch (Exception e) {
      handleMessagingApiException(e);
    }
    log.info("pushMessage結束...");
  }

  /**
   * Line - Reply message: base on replyToken
   * @param replyToken
   * @param messages
   */
  public void replyMessage(String replyToken, List<Message> messages) {
    try {
      ReplyMessageRequest request = new ReplyMessageRequest.Builder(replyToken, messages).build();
      CLIENT.replyMessage(request).get();
    } catch (Exception e) {
      handleMessagingApiException(e);
    }
  }

  /**
   * Line - Multicast message: One-to-many (Targets a list of user IDs)
   * @param users
   * @param messages
   */
  public void multicaseMessage(List<String> users, List<Message> messages) {
    try {
      MulticastRequest request = new MulticastRequest.Builder(messages, users).build();
      CLIENT.multicast(UUID.randomUUID(), request).get();
    } catch (Exception e) {
      handleMessagingApiException(e);
    }
  }

  private void handleMessagingApiException(Exception e) {
    e.printStackTrace();
    if (e.getCause() instanceof MessagingApiClientException exception) {
      log.error("Error http status code: {}", exception.getCode());
      log.error("Error response: {}", exception.getDetails());
      log.error("Error message: {}", exception.getMessage());
    }
  }

  public void notifyUser(LineInfoDto lineInfoDto, String message) {
    log.info("lineInfo: {}", lineInfoDto);
    message += "\r\nSend from multicaseMessage";
    multicaseMessage(List.of(lineInfoDto.getUserId()), List.of(new TextMessage(message)));
  }

  public void replyUser(LineInfoDto lineInfoDto, String message) {
    log.info("lineInfo: {}", lineInfoDto);
    message += "\r\nSend from replyMessage";
    replyMessage(lineInfoDto.getReplyToken(), List.of(new TextMessage(message)));
  }
}
