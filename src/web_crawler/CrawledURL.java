package web_crawler;

import java.net.URL;
import java.util.ArrayList;

public class CrawledURL {
	private URL currURL;
	private CrawledURL parent;
	private ArrayList<CrawledURL> childUrls;
	private int depth;
	
	CrawledURL(URL url, CrawledURL parent) {
		this.currURL = url;
		this.parent = parent;
		this.childUrls = new ArrayList<CrawledURL>();
		
		// set depth for crawled URL
		if(this.parent == null) {
			this.depth = 0;
		} else {
			this.depth = this.parent.depth + 1;
		}
	}
	
	public URL getURL() {
		return this.currURL;
	}
	
	public CrawledURL getParentURL() {
		return this.parent;
	}
	
	public ArrayList<CrawledURL> getChildUrls() {
		return this.childUrls;
	}
	
	public void setChildUrls(ArrayList<URL> urls) {
		for(URL url:urls) {
			this.childUrls.add(new CrawledURL(url, this));
		}
	}
	
	// print crawl map
	public void printCrawlMap() {
		String leftPad = new String(new char[this.depth]).replace("\0", " > ");
		System.out.println(leftPad + System.identityHashCode(this.currURL) + ": " + this.currURL);
		
		if(!this.childUrls.isEmpty()) {
			for(CrawledURL childurl:this.childUrls) {
				childurl.printCrawlMap();
			}
		}
	}
	
	@Override
	public String toString() {
		return this.currURL != null ? this.currURL.toString() : null;
	}
}
