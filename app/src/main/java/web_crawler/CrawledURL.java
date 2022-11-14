package web_crawler;

import java.net.URL;
import java.util.ArrayList;

/*
 * A tree to hold all crawled URLs,
 * navigable from the root (seed URL)
 */
public class CrawledURL {
	private URL currURL;
	private CrawledURL parent;
	private String filePath;

	private ArrayList<CrawledURL> childUrls;
	private int depth;

	CrawledURL(URL url, CrawledURL parent) {
		this.currURL = url;
		this.parent = parent;
		this.childUrls = new ArrayList<CrawledURL>();

		// set depth for crawled URL
		this.depth = (this.parent == null) ? 0 : this.parent.depth + 1;
		this.filePath = (this.parent == null) ? "crawl/seed"
				: this.parent.filePath + "/" + Utility.getSanitizedFilename(this.currURL);
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

	public String getFilePath() {
		return this.filePath + ".html";
	}

	public void setChildUrls(ArrayList<URL> urls) {
		for (URL url : urls) {
			this.childUrls.add(new CrawledURL(url, this));
		}
	}

	// print crawl map
	public void printCrawlMap() {
		String leftPad = new String(new char[this.depth]).replace("\0", " > ");
		// System.identityHashCode(this.currURL) can be used for checking if unique
		System.out.println(leftPad + this.currURL);

		if (!this.childUrls.isEmpty()) {
			for (CrawledURL childurl : this.childUrls) {
				childurl.printCrawlMap();
			}
		}
	}

	@Override
	public String toString() {
		return this.currURL != null ? this.currURL.toString() : null;
	}
}
