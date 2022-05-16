package web_crawler;

import java.net.URL;
import java.util.ArrayList;

public class CrawledURL {
	private URL currURL;
	private CrawledURL parent;
	private ArrayList<CrawledURL> childUrls;
	
	private static int recDepth = 0;
	
	CrawledURL(URL url, CrawledURL parent) {
		this.currURL = url;
		this.parent = parent;
		this.childUrls = new ArrayList<CrawledURL>();
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
		String leftPad = new String(new char[recDepth]).replace("\0", " > ");
		System.out.println(leftPad + System.identityHashCode(this.currURL) + ": " + this.currURL);
		
		if(!this.childUrls.isEmpty()) {
			recDepth += 1;
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
