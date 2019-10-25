package com.wiprodigital.webcrawler.service;

import com.google.common.collect.Sets;
import com.wiprodigital.webcrawler.model.WebPageInformation;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.Map;
import java.util.regex.Pattern;

public class WebCrawlerWorker extends WebCrawler {
    private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");

    private String baseUrl;
    private Map<String, WebPageInformation> domainPagesInformation;

    WebCrawlerWorker(String baseUrl, Map<String, WebPageInformation> domainPagesInformation) {
        this.baseUrl = baseUrl;
        this.domainPagesInformation = domainPagesInformation;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        boolean isDomainLink = urlString.startsWith(baseUrl);
        boolean isStaticContent = EXCLUSIONS.matcher(urlString).matches();

        addDataFromCurrentCrawlingLinkOfPage(referringPage, urlString, isDomainLink, isStaticContent);

        return !isStaticContent && isDomainLink;
    }

    private void addDataFromCurrentCrawlingLinkOfPage(Page referringPage, String currentCrawlingLink, boolean isDomainLink,
                                                      boolean isStaticContent) {

        String referringUrl = referringPage.getWebURL().getURL();
        if(!domainPagesInformation.containsKey(referringUrl)) {
            domainPagesInformation.put(referringUrl, new WebPageInformation(referringUrl,
                    Sets.newConcurrentHashSet(), Sets.newConcurrentHashSet(), Sets.newConcurrentHashSet()));
        }

        if(isStaticContent) {
            domainPagesInformation.get(referringUrl).getStaticContents().add(currentCrawlingLink);
        } else if(isDomainLink) {
            domainPagesInformation.get(referringUrl).getDomainLinks().add(currentCrawlingLink);
        } else {
            domainPagesInformation.get(referringUrl).getExternalLinks().add(currentCrawlingLink);
        }
    }
}
