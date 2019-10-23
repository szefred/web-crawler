package com.wiprodigital.webcrawler.service;

import com.wiprodigital.webcrawler.model.WebPageInformation;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class WebSiteCrawler {

    public WebPageInformation crawl() {
        return new WebPageInformation("http://localhost/", null,
                Collections.singletonList("http://google.com"),
                Collections.singletonList("http://localhost/image.png"));
    }

}
