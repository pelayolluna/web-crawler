package com.pelayolluna.crawler.test;

import com.pelayolluna.crawler.core.WebCrawler;

public class CrawlerTest {

	public static void main(String[] args) {
		new WebCrawler().webCrawlerSearch("http://www.upm.es/", "universidad");
	}

}
