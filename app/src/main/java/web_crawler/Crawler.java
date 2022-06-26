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
	private int crawlDepth;
	
	private static final int maxDepth = 2;
	private static final int maxLinks = 500;
	// private static final int nThreads = 4;
	
	Crawler(String seedURL) {
		this.id = UUID.randomUUID().toString();
		init(seedURL);
	}
	
	// can be used to initialize same Crawler again
	public void init(String seedURL) {
		this.crawlDepth = maxDepth;
		this.seedURL = seedURL;
		this.startURL = new CrawledURL(this.getSeedURL(), null);
		
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
		if(crawlDepth > maxDepth) {
			System.err.println("Depth cannot be set greater than 4");
			System.exit(-1);
		} else if (crawlDepth < 1) {
			System.err.println("Depth cannot be set lesser than 1");
			System.exit(-1);
		}
		this.crawlDepth = crawlDepth;
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
	
	public void crawl() {
		System.out.println("Crawler running...");
		crawl(this.startURL, this.crawlDepth);
		System.out.println("");
	}
	
	public void crawl(CrawledURL curl, int depth) {
		// initialize download
		DownloadURL urlD = new DownloadURL();
		urlD.init(curl.getURL(), curl.getFilePath());
		
		if(depth > 0) {
			// get child URLs and store
			ArrayList<URL> children = urlD.download();
			this.updateLinks(children);
			curl.setChildUrls(children);
		
			// recursively crawl child URLs
			for(CrawledURL childurl:curl.getChildUrls()) {
				crawl(childurl, depth - 1);
			}
		}
	}
	
}
