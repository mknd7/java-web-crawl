package web_crawler;

import java.net.URL;

public class URLPage {
	
	private URL pageURL;
	private int numOutlinks;
	private int pageFileSize;
	private boolean https;
	
	// private String contentType;
	// private String fileExtension;
	
	URLPage(URL pageURL, int numOutlinks, int pageFileSize) {
		this.pageURL = pageURL;
		this.numOutlinks = numOutlinks;
		this.pageFileSize = pageFileSize;
		this.https = (pageURL.toString().indexOf("https://") != -1) ? true : false;
	}
	
	public URL getURL() {
		return this.pageURL;
	}
	
	public int getNumOutlinks() {
		return this.numOutlinks;
	}
	
	public int getPageFileSize() {
		return this.pageFileSize;
	}
	
	public boolean getHttps() {
		return this.https;
	}
	
	@Override
	public String toString() {
		return "URL: " + this.pageURL
				+ ", Number of outlinks: " + this.numOutlinks
				+ ", Page filesize: " + this.pageFileSize
				+ ", HTTPS?: " + this.https;
	}
	
	public String getMetadata() {
		return this.toString();
	}
	
	public void printMetadata() {
		System.out.println("URL: " + this.pageURL);
		System.out.println("Number of outlinks: " + this.numOutlinks);
		System.out.println("Page filesize: " + this.pageFileSize);
		System.out.println("HTTPS?: " + this.https);
	}
}
