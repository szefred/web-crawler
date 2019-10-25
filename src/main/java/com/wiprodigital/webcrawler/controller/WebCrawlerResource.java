package com.wiprodigital.webcrawler.controller;

import com.wiprodigital.webcrawler.model.WebPageInformation;
import com.wiprodigital.webcrawler.service.WebSiteCrawler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebCrawlerResource {
    private WebSiteCrawler webSiteCrawler;

    public WebCrawlerResource(WebSiteCrawler webSiteCrawler) {
        this.webSiteCrawler = webSiteCrawler;
    }

    @GetMapping("/websites")
    Map<String, WebPageInformation> getWebsiteInformation(@RequestParam(value = "url") String websiteUrl) {
        return this.webSiteCrawler.crawl(websiteUrl);
    }
}
