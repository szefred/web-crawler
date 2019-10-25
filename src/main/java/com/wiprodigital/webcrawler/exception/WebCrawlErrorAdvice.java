package com.wiprodigital.webcrawler.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class WebCrawlErrorAdvice {

    @ExceptionHandler(CrawlControllerConstructException.class)
    protected ResponseEntity<String> handleCrawlControllerContructException(CrawlControllerConstructException e) {
        log.error("CrawlController : ", e);
        return new ResponseEntity<>("Can not create CrawlController.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
