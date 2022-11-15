package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/*
 * A metadata class for every URL (page),
 * useful for having a metadata dict of URLs
 */
public class URLMetadata {

	private URL pageURL;
	private int numInlinks;
	private int numOutlinks;
	private String pageFileSize;
	private boolean https;
	private boolean crawled;

	// private String contentType;
	// private String fileExtension;

	private URLMetadata(String pageURL, int numInlinks) throws MalformedURLException {
		this.pageURL = new URL(pageURL);
		this.https = this.pageURL.getProtocol().equals("https") ? true : false;
		this.crawled = false;
		this.numInlinks = numInlinks;
	}

	public void setNumInlinks(int numInlinks) {
		this.numInlinks = numInlinks;
	}

	public void setNumOutlinks(int numOutlinks) {
		this.numOutlinks = numOutlinks;
	}

	public void setPageFileSize(String pageFileSize) {
		this.pageFileSize = pageFileSize;
	}

	public void setPageCrawled() {
		this.crawled = true;
	}

	public URL getURL() {
		return this.pageURL;
	}

	public int getNumInlinks() {
		return this.numInlinks;
	}

	public int getNumOutlinks() {
		return this.numOutlinks;
	}

	public String getPageFileSize() {
		return this.pageFileSize;
	}

	public boolean getHttps() {
		return this.https;
	}

	public static URLMetadata createMetadataObject(
			HashMap<String, URLMetadata> crawlDict,
			String seedURL, String curlURL) throws MalformedURLException {

		URLMetadata currURLPage = null;

		if (crawlDict.containsKey(curlURL)) {
			currURLPage = crawlDict.get(curlURL);
		} else if (curlURL.equals(seedURL)) {
			currURLPage = new URLMetadata(seedURL, -1);
		} else {
			currURLPage = new URLMetadata(curlURL, 0);
		}
		return currURLPage;
	}

	@Override
	public String toString() {
		return this.pageURL + ", Number of inlinks: " + this.numInlinks
				+ (this.crawled ? (", Number of outlinks: " + this.numOutlinks) : "")
				+ ", Page filesize: " + this.pageFileSize
				+ ", HTTPS?: " + this.https
				+ (!this.crawled ? ", not crawled" : "");
	}

	public String getMetadata() {
		return this.toString();
	}

}
