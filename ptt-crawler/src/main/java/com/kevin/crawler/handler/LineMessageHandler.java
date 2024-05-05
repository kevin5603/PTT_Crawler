package com.kevin.crawler.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.crawler.model.line.LineRequestBody;
import com.kevin.crawler.model.line.dto.LineInfoDto;
import com.kevin.crawler.service.line.LineCommandDispatcherService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * line-bot controller 指令: 註冊關鍵字
 */
public class LineMessageHandler implements RequestHandler<Map<String, Object>, Void> {

  private static final Logger log = LoggerFactory.getLogger(LineMessageHandler.class);
  private final LineCommandDispatcherService lineCommandDispatcherService = new LineCommandDispatcherService();


  @Override
  public Void handleRequest(Map<String, Object> input, Context context) {
    try {
      log.info("LineMessageHandler 開始執行...");
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
      mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

      LineRequestBody request = mapper.convertValue(input, LineRequestBody.class);

      LineInfoDto dto = request.toDto();
      lineCommandDispatcherService.messageDispatcher(dto);
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
    }
    log.info("LineMessageHandler 執行結束...");

    return null;
  }

}
