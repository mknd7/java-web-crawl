package web_crawler;

import java.net.URL;
import java.util.ArrayList;

public class DownloadURL {
	
	private URL parent;
	private Crawler c;
	private ArrayList<URL> crawlResults;
	
	DownloadURL(Crawler c) {
		this.parent = null;
		this.c = c;
	}
	
	public void download(String filePath) {
		String page = Utility.downloadPage(this.c.getSeedURL(), filePath);
		ArrayList<URL> urls = Utility.getURLs(page);
		for(URL url:urls) {
			System.out.println(url.toString());
		}
		System.out.println("Fize size is " + Utility.getFormattedSize(filePath));
	}

}
