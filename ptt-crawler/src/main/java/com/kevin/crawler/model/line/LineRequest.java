package com.kevin.crawler.model.line;


import com.fasterxml.jackson.annotation.JsonProperty;

public class LineRequest {
  private String resource;
  private String httpMethod;
  @JsonProperty(value = "headers")
  private LineHeader headers;
  @JsonProperty(value = "body")
  private LineRequestBody body;
  private String keyword;

  public LineRequest() {}

  public LineRequest(String resource, String httpMethod, LineHeader headers, LineRequestBody body,
    String keyword) {
    this.resource = resource;
    this.httpMethod = httpMethod;
    this.headers = headers;
    this.body = body;
    this.keyword = keyword;
  }

  @Override
  public String toString() {
    return "LineRequest{" +
      "resource='" + resource + '\'' +
      ", httpMethod='" + httpMethod + '\'' +
      ", headers=" + headers +
      ", body=" + body +
      ", keyword='" + keyword + '\'' +
      '}';
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public LineHeader getHeaders() {
    return headers;
  }

  public void setHeaders(LineHeader headers) {
    this.headers = headers;
  }

  public LineRequestBody getBody() {
    return body;
  }

  public void setBody(LineRequestBody body) {
    this.body = body;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
}
