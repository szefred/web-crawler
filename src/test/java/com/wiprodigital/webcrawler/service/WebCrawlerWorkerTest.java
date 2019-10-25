package com.wiprodigital.webcrawler.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.wiprodigital.webcrawler.model.WebPageInformation;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class WebCrawlerWorkerTest {
    private WebCrawlerWorker webCrawlerWorker;
    private Map<String, WebPageInformation> domainPagesInformation;
    private String baseUrl = "http://localhost";

    @Before
    public void setUp() {

    }

    @Test
    public void shouldVisitAndAddToDomainLinksWhenDomainUrlAndNoStaticContent() {
        // given
        domainPagesInformation = Maps.newConcurrentMap();
        webCrawlerWorker = new WebCrawlerWorker(baseUrl, domainPagesInformation);
        WebURL referringUrl = new WebURL();
        referringUrl.setURL("http://localhost");
        Page referringPage = new Page(referringUrl);
        WebURL url = new WebURL();
        url.setURL("http://localhost/contact.html");

        // when
        boolean result = webCrawlerWorker.shouldVisit(referringPage, url);

        // then
        assertThat(result).isTrue();
        assertThat(domainPagesInformation.get("http://localhost").getUrl()).isEqualTo("http://localhost");
        assertThat(domainPagesInformation.get("http://localhost").getDomainLinks()).containsExactly("http://localhost/contact.html");
        assertThat(domainPagesInformation.get("http://localhost").getStaticContents()).isEmpty();
        assertThat(domainPagesInformation.get("http://localhost").getExternalLinks()).isEmpty();
    }

    @Test
    public void shouldNotVisitAndAddToExternalLinksUrlWhenExternalUrlAndNoStaticContent() {
        // given
        domainPagesInformation = Maps.newConcurrentMap();
        webCrawlerWorker = new WebCrawlerWorker(baseUrl, domainPagesInformation);
        WebURL referringUrl = new WebURL();
        referringUrl.setURL("http://localhost");
        Page referringPage = new Page(referringUrl);
        WebURL url = new WebURL();
        url.setURL("https://wiprodigital.com/contact.html");

        // when
        boolean result = webCrawlerWorker.shouldVisit(referringPage, url);

        // then
        assertThat(result).isFalse();
        assertThat(domainPagesInformation.get("http://localhost").getUrl()).isEqualTo("http://localhost");
        assertThat(domainPagesInformation.get("http://localhost").getDomainLinks()).isEmpty();
        assertThat(domainPagesInformation.get("http://localhost").getStaticContents()).isEmpty();
        assertThat(domainPagesInformation.get("http://localhost").getExternalLinks()).containsExactly("https://wiprodigital.com/contact.html");
    }

    @Test
    public void shouldNotVisitAndAddToStaticContentsWhenDomainUrlAndStaticContent() {
        // given
        domainPagesInformation = Maps.newConcurrentMap();
        webCrawlerWorker = new WebCrawlerWorker(baseUrl, domainPagesInformation);
        WebURL referringUrl = new WebURL();
        referringUrl.setURL("http://localhost");
        Page referringPage = new Page(referringUrl);
        WebURL url = new WebURL();
        url.setURL("https://localhost/contact.pdf");

        // when
        boolean result = webCrawlerWorker.shouldVisit(referringPage, url);

        // then
        assertThat(result).isFalse();
        assertThat(domainPagesInformation.get("http://localhost").getUrl()).isEqualTo("http://localhost");
        assertThat(domainPagesInformation.get("http://localhost").getDomainLinks()).isEmpty();
        assertThat(domainPagesInformation.get("http://localhost").getStaticContents()).containsExactly("https://localhost/contact.pdf");
        assertThat(domainPagesInformation.get("http://localhost").getExternalLinks()).isEmpty();
    }

    @Test
    public void shouldVisitAndAddToExistingDomainLinksWhenDomainUrlAndNoStaticContent() {
        // given
        domainPagesInformation = Maps.newConcurrentMap();
        domainPagesInformation.put("http://localhost", new WebPageInformation("http://localhost",
                Sets.newHashSet("http://localhost/other.html"), Sets.newConcurrentHashSet(), Sets.newConcurrentHashSet()));
        webCrawlerWorker = new WebCrawlerWorker(baseUrl, domainPagesInformation);
        WebURL referringUrl = new WebURL();
        referringUrl.setURL("http://localhost");
        Page referringPage = new Page(referringUrl);
        WebURL url = new WebURL();
        url.setURL("http://localhost/contact.html");


        // when
        boolean result = webCrawlerWorker.shouldVisit(referringPage, url);

        // then
        assertThat(result).isTrue();
        assertThat(domainPagesInformation.get("http://localhost").getDomainLinks()).hasSize(2);
        assertThat(domainPagesInformation.get("http://localhost").getExternalLinks()).hasSize(0);
        assertThat(domainPagesInformation.get("http://localhost").getStaticContents()).hasSize(0);
    }
}