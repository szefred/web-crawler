package com.wiprodigital.webcrawler.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.wiprodigital.webcrawler.model.WebPageInformation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebSiteCrawlerIT {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(new WireMockConfiguration().dynamicPort());
    @Autowired
    WebSiteCrawler webSiteCrawler;

    @Test
    public void shouldCorrectlyCrawlMockWebsite() {
        // given
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")
                        .withBody("<html><body><h1>Hell in home page</h1>" +
                                "<a href='contact.html'>Contact</a>" +
                                "</body></html>")));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/contact.html"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")
                        .withBody("<html><body><h1>Hello in contact page</h1>" +
                                "<img src='/company.png'>" +
                                "<a href='https://www.google.pl/maps'>Show on map</a>" +
                                "<a href='offer.html'>Offer</a>" +
                                "<a href='other.html'>Come back to home</a>" +
                                "</body></html>")));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/other.html"))
                .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "text/html")));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/offer.html"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/robots.txt"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")));

        // when
        Map<String, WebPageInformation> result =  webSiteCrawler.crawl("http://localhost:" + wireMockRule.port());

        // then
        String localhost = "http://localhost:" + wireMockRule.port() + "/";
        assertThat(result.get(localhost).getUrl()).isEqualTo(localhost);
        assertThat(result.get(localhost).getExternalLinks()).isEmpty();
        assertThat(result.get(localhost).getStaticContents()).isEmpty();
        assertThat(result.get(localhost).getDomainLinks()).containsExactly(localhost + "contact.html");

        assertThat(result.get(localhost + "contact.html").getUrl()).isEqualTo(localhost + "contact.html");
        assertThat(result.get(localhost + "contact.html").getExternalLinks()).containsExactly("https://www.google.pl/maps");
        assertThat(result.get(localhost + "contact.html").getStaticContents()).containsExactly(localhost + "company.png");
        assertThat(result.get(localhost + "contact.html").getDomainLinks()).containsExactlyInAnyOrder(localhost + "offer.html", localhost + "other.html");
    }
}
