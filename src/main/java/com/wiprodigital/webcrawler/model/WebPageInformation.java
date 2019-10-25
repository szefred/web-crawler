package com.wiprodigital.webcrawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class WebPageInformation {
    private String url;
    private Set<String> domainLinks;
    private Set<String> externalLinks;
    private Set<String> staticContents;
}
