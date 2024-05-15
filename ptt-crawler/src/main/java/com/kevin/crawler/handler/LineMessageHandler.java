package com.kevin.crawler.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.crawler.model.line.LineRequestBody;
import com.kevin.crawler.model.line.dto.LineInfoDto;
import com.kevin.crawler.service.line.LineCommandDispatcherService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineMessageHandler implements
  RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final Logger log = LoggerFactory.getLogger(LineMessageHandler.class);
  private final LineCommandDispatcherService lineCommandDispatcherService = new LineCommandDispatcherService();

  @Override
  public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent request, final Context context) {
    long start = System.currentTimeMillis();
    APIGatewayProxyResponseEvent response = createAPIGatewayResponse();
    try {
      log.info("LineMessageHandler 開始執行...");
      log.info("api request: {}", request);
      processRequest(request);

      String completionMessage = "LineMessageHandler 執行結束...";
      log.info(completionMessage);
      response.setBody(completionMessage);
      response.setStatusCode(200);
    } catch (Exception e) {
      String errorMessage = handleException(e);
      response.setBody(errorMessage);
      response.setStatusCode(500);
    }
    long end = System.currentTimeMillis();
    log.info("uouo demo cicd");
    log.info("LineMessageHandler結束...耗時: {}秒", (end - start) / 1000.0);
    return response;
  }

  private static String handleException(Exception e) {
    e.printStackTrace();
    String errorMessage = e.getMessage();
    log.error(errorMessage);
    return errorMessage;
  }

  private void processRequest(APIGatewayProxyRequestEvent request) throws JsonProcessingException {
    String body = request.getBody();
    ObjectMapper mapper = new ObjectMapper();
    LineRequestBody requestBody = mapper.readValue(body, LineRequestBody.class);
    if (requestBody != null && !requestBody.getEvents().isEmpty()) {
      LineInfoDto dto = requestBody.toDto();
      lineCommandDispatcherService.messageDispatcher(dto);
    }
  }

  private APIGatewayProxyResponseEvent createAPIGatewayResponse() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("X-Custom-Header", "application/json");
    return new APIGatewayProxyResponseEvent()
      .withHeaders(headers);
  }
}
