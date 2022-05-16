package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class Crawler {
	
	private String id;
	private String seedURL;
	private int crawlDepth;
	
	private CrawledURL startURL;
	private PageHierarchy p = new PageHierarchy();
	private ArrayList<URL> allLinks;
	
	private static final int maxURLLinks = 200;
	private static final int maxTotal = 1000;
	
	Crawler() {
		this.crawlDepth = 2; // default depth
		this.id = UUID.randomUUID().toString();
		this.seedURL = "https://www.google.com";
		this.allLinks = new ArrayList<URL>();
		try {
			this.allLinks.add(new URL(this.seedURL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
	
	public void printCrawlMap() {
		this.startURL.printCrawlMap();
	}
	
	public void removeDuplicates(ArrayList<URL> children) {
		ArrayList<URL> duplicates = new ArrayList<URL>();
		for(URL childurl:children) {
			if(!allLinks.contains(childurl)) {
				this.allLinks.add(childurl);
			} else {
				duplicates.add(childurl);
			}
		}
		children.removeAll(duplicates);
	}
	
	public void crawl(CrawledURL curl) {
		// first crawl (seed) started by passing null
		if(curl == null) {
			this.startURL = new CrawledURL(this.getSeedURL(), null); 
			curl = this.startURL;
		}
		
		DownloadURL urlD = new DownloadURL(this);
		urlD.init(curl.getURL(), p.getNextPageID() + ".html");
		
		// get child URLs, remove duplicates and set
		ArrayList<URL> children = urlD.download();
		this.removeDuplicates(children);
		curl.setChildUrls(children);
		
		// check depth and recursively crawl children
		if(--this.crawlDepth > 0) {
			for(CrawledURL childurl:curl.getChildUrls()) {
				crawl(childurl);
			}
		}
	}
	
}
