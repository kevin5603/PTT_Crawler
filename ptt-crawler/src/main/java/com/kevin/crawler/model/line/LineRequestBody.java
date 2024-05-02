package com.kevin.crawler.model.line;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kevin.crawler.model.line.dto.LineInfoDto;
import java.util.List;

public class LineRequestBody {
  private String destination;
  @JsonProperty(value = "events")
  private List<LineEvent> events;

  public LineRequestBody() {
  }

  public LineRequestBody(String destination, List<LineEvent> events) {
    this.destination = destination;
    this.events = events;
  }

  @Override
  public String toString() {
    return "LineRequestBody{" +
      "destination='" + destination + '\'' +
      ", events=" + events +
      '}';
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public List<LineEvent> getEvents() {
    return events;
  }

  public void setEvents(List<LineEvent> events) {
    this.events = events;
  }

  // TODO 尚未完成 測試
  public LineInfoDto toDto() {
    List<LineInfoDto> list = this.events.stream().map(event ->
      new LineInfoDto(event.getMessage(), event.getSource().getUserId(), event.getReplyToken())).toList();
    return list.getFirst();
  }


}
