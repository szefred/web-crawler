package com.wiprodigital.webcrawler.model;

import lombok.Value;

import java.util.List;

@Value
public class WebPageInformation {
    private String url;
    private List<WebPageInformation> domainLinks;
    private List<String> externalLinks;
    private List<String> staticContents;
}
