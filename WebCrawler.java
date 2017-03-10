package com.pelayolluna.crawler.core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public final class WebCrawler {

	private final ResourceBundle rbApplication = ResourceBundle.getBundle("application");
	private final ResourceBundle rbLiterals = ResourceBundle.getBundle("literals");
	private final Set<String> pagesGetted = new HashSet<>();
	private final List<String> pagesList = new LinkedList<>();

	/**
	 * 
	 * @return next website to visit
	 */
	private String nextUrlToSearch() {
		String nextUrl = "";

		while (this.pagesGetted.contains(nextUrl)) {
			nextUrl = this.pagesList.remove(0);
		}

		this.pagesGetted.add(nextUrl);

		return nextUrl;
	}

	/**
	 * 
	 * @param url
	 *            WebCrawlerElement
	 * @param keyWord
	 *            Keyword to search
	 */
	public void webCrawlerSearch(String website, String keyWord) {
		while (this.pagesGetted.size() < Integer.parseInt(rbApplication.getString("max-pages"))) {
			String currentWebsite;
			WebCrawlerElement webCrawlerElement = new WebCrawlerElement();

			if (this.pagesList.isEmpty()) {
				currentWebsite = website;
				this.pagesGetted.add(website);
			} else {
				currentWebsite = this.nextUrlToSearch();
			}
			webCrawlerElement.crawlWebsite(currentWebsite);

			if (webCrawlerElement.searchWord(keyWord)) {
				System.out.println(String.format("\n" + rbLiterals.getString("keywordFound"), keyWord, currentWebsite));
				break;
			}
			this.pagesList.addAll(webCrawlerElement.getLinks());
		}
		System.out.printf("\n" + rbLiterals.getString("websitesVisited"), this.pagesGetted.size());
	}

}
