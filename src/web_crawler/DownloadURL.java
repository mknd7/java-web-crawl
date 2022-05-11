package web_crawler;

import java.net.URL;
import java.util.ArrayList;

public class DownloadURL {
	
	private URL parent;
	private Crawler crawlInitiator;
	private ArrayList<URL> crawlResults;
	
	DownloadURL(Crawler crawlInitiator) {
		this.parent = null;
		this.crawlResults = new ArrayList<URL>();
		this.crawlInitiator = crawlInitiator;
	}
	
	public ArrayList<URL> download(String filePath) {
		startDownload(filePath);
		return this.getNextDownloadLinks();
	}
	
	public void startDownload(String filePath) {
		String page = Utility.downloadPage(this.crawlInitiator.getSeedURL(), filePath);
		ArrayList<URL> urls = Utility.getURLs(page);
		
		if(!urls.isEmpty()) {
			crawlResults.addAll(urls);
		}
		System.out.println("Fize size is " + Utility.getFormattedSize(filePath));
	}
	
	public ArrayList<URL> getNextDownloadLinks() {
		return this.crawlResults;
	}

}
