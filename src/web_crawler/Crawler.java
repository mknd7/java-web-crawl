package web_crawler;

import java.util.UUID;

public class Crawler {
	
	private String id;
	private String seedURL;
	private int crawlDepth;
	
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
	
	public String getSeedURL() {
		return this.seedURL;
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
	
	public void crawl() {
		DownloadURL dl = new DownloadURL(this);
		dl.download("./google.html");
	}

}
