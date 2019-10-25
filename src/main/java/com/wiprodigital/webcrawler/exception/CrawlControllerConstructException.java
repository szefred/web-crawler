package com.wiprodigital.webcrawler.exception;

public class CrawlControllerConstructException extends RuntimeException {
    private static String MESSAGE = "Can not construct CrawlController";

    public CrawlControllerConstructException(Throwable throwable) {
        super(MESSAGE, throwable);
    }
}
