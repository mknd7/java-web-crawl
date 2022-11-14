package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/*
 * A metadata class for every URL (page),
 * useful for having a metadata dict of URLs
 */
public class URLPage {

	private URL pageURL;
	private int numInlinks;
	private int numOutlinks;
	private long pageFileSize;
	private boolean https;
	private boolean crawled;

	// private String contentType;
	// private String fileExtension;

	private URLPage(String pageURL, int numInlinks) throws MalformedURLException {
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

	public void setPageFileSize(long pageFileSize) {
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

	public long getPageFileSize() {
		return this.pageFileSize;
	}

	public boolean getHttps() {
		return this.https;
	}

	public static URLPage createMetadataObject(
			HashMap<String, URLPage> crawlDict,
			String seedURL, String curlURL) throws MalformedURLException {

		URLPage currURLPage = null;

		if (crawlDict.containsKey(curlURL)) {
			currURLPage = crawlDict.get(curlURL);
		} else if (curlURL.equals(seedURL)) {
			currURLPage = new URLPage(seedURL, -1);
		} else {
			currURLPage = new URLPage(curlURL, 0);
		}
		return currURLPage;
	}

	@Override
	public String toString() {
		return "URL: " + this.pageURL
				+ ", Number of inlinks: " + this.numInlinks
				+ ", Number of outlinks: " + this.numOutlinks
				+ ", Page filesize: " + this.pageFileSize + " KB"
				+ ", HTTPS?: " + this.https
				+ ", Crawled: " + this.crawled;
	}

	public String getMetadata() {
		return this.toString();
	}

	public void printMetadata() {
		System.out.println("URL: " + this.pageURL);
		System.out.println("Number of inlinks: " + this.numInlinks);
		System.out.println("Number of outlinks: " + this.numOutlinks);
		System.out.println("Page filesize: " + this.pageFileSize + " KB");
		System.out.println("HTTPS?: " + this.https);
		System.out.println("Crawled: " + this.crawled);
	}
}
