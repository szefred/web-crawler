package com.wiprodigital.webcrawler.service;

import com.google.common.collect.Maps;
import com.wiprodigital.webcrawler.model.WebPageInformation;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class WebSiteCrawler {
    @Value("${website.crawler.number-workers}")
    private Integer numberOfCrawlerWorkers;
    private CrawlController crawlController;

    public WebSiteCrawler(CrawlController crawlController) {
        this.crawlController = crawlController;
    }

    public Map<String, WebPageInformation> crawl(String baseUrl) {
        Map<String, WebPageInformation> webSiteInformation = Maps.newConcurrentMap();

        crawlController.addSeed(baseUrl);
        CrawlController.WebCrawlerFactory<WebCrawler> factory = () -> new WebCrawlerWorker(baseUrl, webSiteInformation);
        crawlController.start(factory, numberOfCrawlerWorkers);

        return webSiteInformation;
    }
}
