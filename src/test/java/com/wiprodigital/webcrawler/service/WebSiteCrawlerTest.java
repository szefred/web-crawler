package com.wiprodigital.webcrawler.service;

import com.wiprodigital.webcrawler.model.WebPageInformation;
import edu.uci.ics.crawler4j.crawler.CrawlController;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class WebSiteCrawlerTest {
    @Mock
    CrawlController crawlController;
    @InjectMocks
    WebSiteCrawler webSiteCrawler;

    @Test
    void shouldReturnWebPageInformation() {
        // given
        crawlController = Mockito.mock(CrawlController.class);
        webSiteCrawler = new WebSiteCrawler(crawlController);
        ReflectionTestUtils.setField(webSiteCrawler, "numberOfCrawlerWorkers", 10);
        String baseUrl = "http://localhost/";

        // when
        Map<String, WebPageInformation> result = webSiteCrawler.crawl(baseUrl);

        // then
        verify(crawlController).addSeed(baseUrl);
        verify(crawlController).start(isA(CrawlController.WebCrawlerFactory.class), eq(10));
        assertThat(result).isEmpty();
    }
}