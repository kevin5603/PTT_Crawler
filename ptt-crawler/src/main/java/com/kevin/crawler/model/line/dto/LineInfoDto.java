package com.kevin.crawler.model.line.dto;

import com.kevin.crawler.model.line.LineMessage;

public class LineInfoDto {
  private LineMessage message;
  private String userId;
  private String replyToken;

  public LineInfoDto() {
  }

  public LineInfoDto(LineMessage message, String userId, String replyToken) {
    this.message = message;
    this.userId = userId;
    this.replyToken = replyToken;
  }

  @Override
  public String toString() {
    return "LineInfoDto{" +
      "message=" + message +
      ", userId='" + userId + '\'' +
      ", replyToken='" + replyToken + '\'' +
      '}';
  }

  public LineMessage getMessage() {
    return message;
  }

  public void setMessage(LineMessage message) {
    this.message = message;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getReplyToken() {
    return replyToken;
  }

  public void setReplyToken(String replyToken) {
    this.replyToken = replyToken;
  }
}
