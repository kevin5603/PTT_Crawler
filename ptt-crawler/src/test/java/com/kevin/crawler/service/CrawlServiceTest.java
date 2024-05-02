package com.kevin.crawler.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.kevin.crawler.model.Crawl;
import com.kevin.crawler.repository.impl.CrawlRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CrawlServiceTest {

  @Mock
  private CrawlRepository crawlRepository;

  @InjectMocks
  private CrawlService crawlService;

  @Test
  void getCrawlList() {
    // Arrange
    List<Crawl> expectedCrawlList = List.of(new Crawl(1, null, null, null), new Crawl(2, null, null, null));
    when(crawlRepository.getAllItem()).thenReturn(expectedCrawlList);

    // Act
    List<Crawl> actualCrawlList = crawlService.getCrawlList();

    // Assert
    assertEquals(expectedCrawlList, actualCrawlList);
  }

  @Test
  void getBoardList() {
    // Arrange
    List<Crawl> expectedCrawlList = List.of(new Crawl(1, "a", null, null), new Crawl(2, "b", null, null),
      new Crawl(3, "b", null, null));
    when(crawlRepository.getAllItem()).thenReturn(expectedCrawlList);
    List<String> expectedBoardList = List.of("a", "b");

//    // Act
    List<String> actualBoardList = crawlService.getBoardList();

//    // Assert
    assertEquals(expectedBoardList, actualBoardList);
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }
}
