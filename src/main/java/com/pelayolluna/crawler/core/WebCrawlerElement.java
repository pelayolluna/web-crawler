package com.pelayolluna.crawler.core;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class WebCrawlerElement {

	private final ResourceBundle rbApplication = ResourceBundle.getBundle("application");
	private final ResourceBundle rbSystem = ResourceBundle.getBundle("system");
	private final ResourceBundle rbLiterals = ResourceBundle.getBundle("literals");
	private final List<String> links = new LinkedList<>();
	private Document htmlPage;

	public List<String> getLinks() {
		return this.links;
	}

	/**
	 * 
	 * @param keyword
	 *            Word to search
	 * @return if website contains the word
	 */
	public boolean searchWord(String keyword) {
		if (this.htmlPage == null) {
			System.out.println("\n" + rbLiterals.getString("parseError"));
			return false;
		}
		System.out.printf(rbLiterals.getString("searchingWord"), keyword);
		return this.htmlPage.body().text().toLowerCase().contains(keyword.toLowerCase());
	}

	/**
	 * 
	 * @param url
	 *            Website to crawl
	 * @return crawl response
	 */
	public boolean crawlWebsite(String website) {
		try {
			// Proxy config
			System.setProperty("http.proxyHost", rbSystem.getString("proxyHost"));
			System.setProperty("http.proxyPort", rbSystem.getString("proxyPort"));
			System.setProperty("http.proxyUser", rbSystem.getString("proxyUser"));
			System.setProperty("http.proxyPassword", rbSystem.getString("proxyPassword"));

			Connection connection = null;

			if (website != null && !"".equals(website)) {
				connection = Jsoup.connect(website).userAgent(rbApplication.getString("user-agent"));
				Document htmlPage = connection.get();
				this.htmlPage = htmlPage;

				if (connection.response().statusCode() == 200) {
					System.out.println("\n" + rbLiterals.getString("websiteGetted") + ": " + website);
				}
				if (!connection.response().contentType().contains("text/html")) {
					System.out.println("\n" + rbLiterals.getString("htmlFormatError"));
					return false;
				}
				Elements linksOnWebsite = htmlPage.select("a[href]");
				System.out.println("Found (" + linksOnWebsite.size() + ") links");

				for (Element link : linksOnWebsite) {
					this.links.add(link.absUrl("href"));
				}
				return true;
			} else
				return false;
		} catch (IOException ioe) {
			return false;
		}
	}

}
