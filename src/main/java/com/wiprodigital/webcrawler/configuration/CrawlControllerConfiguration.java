package com.wiprodigital.webcrawler.configuration;

import com.wiprodigital.webcrawler.exception.CrawlControllerConstructException;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.UUID;

@Configuration
public class CrawlControllerConfiguration {
    @Value("${website.crawler.storage}")
    private String crawlerStorage;

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CrawlController crawlController() {
        CrawlConfig config = prepareCrawlConfig();
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtServer robotstxtServer= new RobotstxtServer(new RobotstxtConfig(), pageFetcher);
        CrawlController controller;
        try {
            controller = new CrawlController(config, pageFetcher, robotstxtServer);
        } catch (Exception e) {
            throw new CrawlControllerConstructException(e);
        }

        return controller;
    }

    private CrawlConfig prepareCrawlConfig() {
        UUID uuid = UUID.randomUUID();
        File crawlStorage = new File(crawlerStorage + "/" + uuid);
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
        return config;
    }
}
