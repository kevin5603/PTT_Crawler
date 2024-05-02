package com.kevin.crawler.model.line;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LineHeader {
  @JsonProperty("content-type")
  private String contentType;
  @JsonProperty("x-line-signature")
  private String xLineSignature;
  @JsonProperty("X-Amzn-Trace-Id")
  private String xAmznTraceId;

  public LineHeader() {
  }

  public LineHeader(String contentType, String xLineSignature, String xAmznTraceId) {
    this.contentType = contentType;
    this.xLineSignature = xLineSignature;
    this.xAmznTraceId = xAmznTraceId;
  }

  @Override
  public String toString() {
    return "LineHeader{" +
      "contentType='" + contentType + '\'' +
      ", xLineSignature='" + xLineSignature + '\'' +
      ", xAmznTraceId='" + xAmznTraceId + '\'' +
      '}';
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getxLineSignature() {
    return xLineSignature;
  }

  public void setxLineSignature(String xLineSignature) {
    this.xLineSignature = xLineSignature;
  }

  public String getxAmznTraceId() {
    return xAmznTraceId;
  }

  public void setxAmznTraceId(String xAmznTraceId) {
    this.xAmznTraceId = xAmznTraceId;
  }
}
