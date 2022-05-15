package web_crawler;

import java.net.URL;
import java.util.ArrayList;

public class DownloadURL {
	
	private URL url;
	private String filePath;
	
	private ArrayList<URL> crawlResults;
	private Crawler crawlInitiator;
	
	DownloadURL(Crawler crawlInitiator) {
		this.crawlInitiator = crawlInitiator;
		this.crawlResults = new ArrayList<URL>();
	}
	
	DownloadURL(Crawler crawlInitiator, URL url, String filePath) {
		this(crawlInitiator);
		init(url, filePath);
	}
	
	public void init(URL url, String filePath) {
		this.url = url;
		this.filePath = filePath;
	}
	
	public ArrayList<URL> download() {
		scanPage();
		return this.getNextDownloadLinks();
	}
	
	public void scanPage() {
		String page = Utility.downloadPage(url, filePath);
		ArrayList<URL> urls = Utility.getURLs(page);
		
		if(!urls.isEmpty()) {
			crawlResults.addAll(urls);
		}
	}
	
	public ArrayList<URL> getNextDownloadLinks() {
		return this.crawlResults;
	}

}
