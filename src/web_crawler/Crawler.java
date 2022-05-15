package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

public class Crawler {
	
	private String id;
	private String seedURL;
	private int crawlDepth;
	 
	private CrawledURL startURL;
	private PageHierarchy p = new PageHierarchy();
	private Set<URL> allLinks;
	
	private static final int maxURLLinks = 200;
	private static final int maxTotal = 1000;
	
	Crawler() {
		this.crawlDepth = 2; // default depth
		this.id = UUID.randomUUID().toString();
		this.seedURL = "https://www.google.com";
		this.allLinks = new HashSet<URL>();
	}
	
	Crawler(String seedURL) {
		this();
		this.seedURL = seedURL;
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
	
	// first crawl starting with seed
	public void startCrawl() {
		this.startURL = new CrawledURL(this.getSeedURL(), null); 
		crawl(this.startURL);
	}
	
	public void crawl(CrawledURL curl) {
		DownloadURL urlD = new DownloadURL(this);
		urlD.init(curl.getURL(), p.getNextPageID() + ".html");
		
		if(allLinks.contains(curl.getURL())) { return; }
		this.allLinks.add(curl.getURL());
		
		// set children for URL
		curl.setChildUrls(urlD.download());
		if(this.crawlDepth > 0) {
			this.crawlDepth -= 1;
			for(CrawledURL childurl:curl.getChildUrls()) {
				crawl(childurl);
			}
		} else {
			this.startURL.printCrawlMap();
		}
	}
	
}
