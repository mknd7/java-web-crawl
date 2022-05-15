package web_crawler;

import java.net.URL;
import java.util.ArrayList;

public class CrawledURL {
	private URL currURL;
	private CrawledURL parent;
	private ArrayList<CrawledURL> childUrls;
	private int recDepth;
	
	CrawledURL(URL url, CrawledURL parent) {
		this.currURL = url;
		this.parent = parent;
		this.childUrls = new ArrayList<CrawledURL>();
		this.recDepth = 0;
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
	
	// print an entire crawl map given
	public void printCrawlMap() {
		String leftPad = new String(new char[this.recDepth]).replace("\0", "  ");
		System.out.println(leftPad + this.currURL);
		this.recDepth++;
		
		if(!this.childUrls.isEmpty()) {
			for(CrawledURL url:this.childUrls) {
				url.printCrawlMap();
			}
		}
	}
	
	@Override
	public String toString() {
		return this.currURL != null ? this.currURL.toString() : null;
	}
}
