package com.kevin.crawler.model.line;

import com.linecorp.bot.webhook.model.DeliveryContext;

public class LineEvent {

  private String type;
  private LineMessage message;
  private String webhookEventId;
  private DeliveryContext deliveryContext;
  private Long timestamp;
  private Source source;
  private String replyToken;
  private String mode;

  public LineEvent() {
  }

  public LineEvent(String type, LineMessage message, String webhookEventId,
    DeliveryContext deliveryContext, Long timestamp, Source source, String replyToken,
    String mode) {
    this.type = type;
    this.message = message;
    this.webhookEventId = webhookEventId;
    this.deliveryContext = deliveryContext;
    this.timestamp = timestamp;
    this.source = source;
    this.replyToken = replyToken;
    this.mode = mode;
  }

  @Override
  public String toString() {
    return "LineEvent{" +
      "type='" + type + '\'' +
      ", message=" + message +
      ", webhookEventId='" + webhookEventId + '\'' +
      ", deliveryContext=" + deliveryContext +
      ", timestamp=" + timestamp +
      ", source=" + source +
      ", replyToken='" + replyToken + '\'' +
      ", mode='" + mode + '\'' +
      '}';
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public LineMessage getMessage() {
    return message;
  }

  public void setMessage(LineMessage message) {
    this.message = message;
  }

  public String getWebhookEventId() {
    return webhookEventId;
  }

  public void setWebhookEventId(String webhookEventId) {
    this.webhookEventId = webhookEventId;
  }

  public DeliveryContext getDeliveryContext() {
    return deliveryContext;
  }

  public void setDeliveryContext(DeliveryContext deliveryContext) {
    this.deliveryContext = deliveryContext;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Source getSource() {
    return source;
  }

  public void setSource(Source source) {
    this.source = source;
  }

  public String getReplyToken() {
    return replyToken;
  }

  public void setReplyToken(String replyToken) {
    this.replyToken = replyToken;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }
}
