package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class Crawler {
	
	private String id;
	private String seedURL;
	
	private CrawledURL startURL;
	private ArrayList<URL> allLinks;
	
	private static int crawlDepth = 3;
	private static final int maxLinks = 1000;
	
	Crawler(String seedURL) {
		this.id = UUID.randomUUID().toString();
		this.seedURL = seedURL;
		this.allLinks = new ArrayList<URL>();
		this.allLinks.add(this.getSeedURL());
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
		} else if (crawlDepth < 1) {
			System.err.println("Depth cannot be set lesser than 1");
			System.exit(-1);
		}
		Crawler.crawlDepth = crawlDepth;
	}
	
	public void printCrawlMap() {
		this.startURL.printCrawlMap();
	}
	
	// updates list of all links and removes duplicates
	public void updateLinks(ArrayList<URL> children) {
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
		urlD.init(curl.getURL(), curl.getFilePath());
		crawlDepth -= 1;
		
		// get child URLs and set for current URL
		ArrayList<URL> children = urlD.download();
		this.updateLinks(children);
		curl.setChildUrls(children);
		
		if(crawlDepth > 0) {
			// recursively crawl child URLs
			for(CrawledURL childurl:curl.getChildUrls()) {
				crawl(childurl);
			}
		}
	}
	
}
