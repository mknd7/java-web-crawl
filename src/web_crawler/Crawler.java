package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class Crawler {
	
	private String id;
	private String seedURL;
	private int crawlDepth;
	
	private ArrayList<URL> nextURLs;
	private CrawledURL startURL;
	
	private static final int maxURLLinks = 200;
	private static final int maxTotal = 1000;
	
	Crawler() {
		this.crawlDepth = 2; // default depth
		this.id = UUID.randomUUID().toString();
		this.seedURL = "https://www.google.com";
	}
	
	Crawler(String seedURL) {
		this();
		this.seedURL = seedURL;
	}
	
	// inner class for each crawled URL
	public class CrawledURL {
		private URL thisURL;
		private CrawledURL parent;
		private ArrayList<CrawledURL> urls;
	}
	
	public URL getSeedURL() {
		URL seedU = null;
		try {
			seedU = new URL(this.seedURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return seedU;
	}
	
	public void setSeedURL(String seedURL) {
		this.seedURL = seedURL;
	}
	
	public void setCrawlDepth(int crawlDepth) {
		if(crawlDepth > 4) {
			System.err.println("Depth cannot be set greater than 4");
			System.exit(-1);
		}
		this.crawlDepth = crawlDepth;
	}
	
	public void startCrawl() {
		DownloadURL seedDownload = new DownloadURL(this);
		seedDownload.init(this.getSeedURL(), "./seed.html");
		this.nextURLs = seedDownload.download();
	}
	
	public void crawl() {
		startCrawl();
		ArrayList<ArrayList<URL>> urlLists = new ArrayList<ArrayList<URL>>();
		PageHierarchy p = new PageHierarchy();
		
		for(URL url:this.nextURLs) {
			System.out.println(url);
			DownloadURL nextURL = new DownloadURL(this);
			nextURL.init(url, p.getNextPageID() + ".html");
			urlLists.add(nextURL.download());
		}
		
		for(ArrayList<URL> list:urlLists) {
			for(URL url:list) {
				System.out.println(url);
			}
		}
	}

}
