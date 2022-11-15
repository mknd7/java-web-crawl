package web_crawler;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/*
 * A tree to hold all crawled URLs,
 * navigable from the root (seed URL)
 */
public class CrawledURL {
	private URL currURL;
	private CrawledURL parent;
	private Set<CrawledURL> childUrls;

	private int depth;
	private String filePath;
	private URLMetadata metadata;

	CrawledURL(URL url, CrawledURL parent) {
		this.currURL = url;
		this.parent = parent;
		this.childUrls = new HashSet<CrawledURL>();

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

	public Set<CrawledURL> getChildUrls() {
		return this.childUrls;
	}

	public String getFilePath() {
		return this.filePath + ".html";
	}

	public URLMetadata getUrlMetadata() {
		return this.metadata;
	}

	public void setChildUrls(Set<URL> urls) {
		for (URL url : urls) {
			this.childUrls.add(new CrawledURL(url, this));
		}
	}

	public void setUrlMetadata(URLMetadata metadata) {
		this.metadata = metadata;
	}

	// print crawl map
	public void printCrawlMap(Boolean withMetadata) {
		String leftPad = new String(new char[this.depth]).replace("\0", " > ");
		// System.identityHashCode(this.currURL) can be used for checking if unique
		if (withMetadata) {
			System.out.println(leftPad + this.metadata.toString());
		} else {
			System.out.println(leftPad + this.currURL);
		}

		if (!this.childUrls.isEmpty()) {
			for (CrawledURL childurl : this.childUrls) {
				childurl.printCrawlMap(withMetadata);
			}
		}
	}

	@Override
	public String toString() {
		return this.currURL != null ? this.currURL.toString() : null;
	}
}
