How to test:

* mvn test

How to build:

* mvn install

How to run application:

* mvn spring-boot:run

After application is ready the following endpoint could be called (eg. by web browser):

* localhost:8080/websites?url=https://wiprodigital.com

TODO:

* add unit tests for CrawlController and WebCrawlerResource
* add e2e test for endpoint /websites?url=http://somepage.com
* improve unit test in WebSiteCrawlerTest to check CrawlController.WebCrawlerFactory arguments

Future improvements:

* validate input param
* enable more options for crawler (name of User Agent, max crawl depth, max size of page to crawl etc)
* store/cache results


I decided to use crawler4j what is good documented (Readme.doc and code examples) 
and popular (3.7k start, 1.8k forks and 28 contributors) library written mainly
by Yasser Ganjisaffar (PhD in Compute Since).