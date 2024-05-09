package com.kevin.crawler.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kevin.crawler.model.line.LineRequestBody;
import com.kevin.crawler.model.line.dto.LineInfoDto;
import com.kevin.crawler.service.line.LineCommandDispatcherService;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineMessageHandler implements
  RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final Logger log = LoggerFactory.getLogger(LineMessageHandler.class);
  private final LineCommandDispatcherService lineCommandDispatcherService = new LineCommandDispatcherService();

  @Override
  public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input,
    final Context context) {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("X-Custom-Header", "application/json");

    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
      .withHeaders(headers);
    try {
      log.info("LineMessageHandler 開始執行...");
      log.info("api input: {}", input);

      String body = input.getBody();
      log.info("api body: {}", body);
      ObjectMapper mapper = new ObjectMapper();
      LineRequestBody request = mapper.readValue(body, LineRequestBody.class);
      if (request != null && !request.getEvents().isEmpty()) {
        LineInfoDto dto = request.toDto();
        lineCommandDispatcherService.messageDispatcher(dto);
      }
    } catch (Exception e) {
      e.printStackTrace();
      String errorMessage = e.getMessage();
      log.error(errorMessage);
      return response
        .withBody(errorMessage)
        .withStatusCode(500);
    }
    String output = "LineMessageHandler 執行結束...";
    log.info(output);
    return response
      .withStatusCode(200)
      .withBody(output);
  }

  public String toString(Object obj) {
    try (StringWriter w = new StringWriter();) {
      new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
      return w.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
