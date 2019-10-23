package com.wiprodigital.webcrawler.service;

import com.wiprodigital.webcrawler.model.WebPageInformation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class WebSiteCrawlerTest {
    private WebSiteCrawler webSiteCrawler = new WebSiteCrawler();

    @Test
    void shouldReturnWebPageInformation() {
        // given

        // when
        WebPageInformation webPageInformation = webSiteCrawler.crawl();

        // then
        assertThat(webPageInformation.getUrl()).isEqualTo("http://localhost/");
        assertThat(webPageInformation.getExternalLinks()).containsExactly("http://google.com");
        assertThat(webPageInformation.getDomainLinks()).isNull();
        assertThat(webPageInformation.getStaticContents()).containsExactly("http://localhost/image.png");

    }
}