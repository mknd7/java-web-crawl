package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class URLScanner {

	private URL url;
	private String filePath;
	private ArrayList<URL> scanResults;

	URLScanner(URL url, String filePath) {
		this.scanResults = new ArrayList<URL>();
		this.url = url;
		this.filePath = filePath;
	}

	public String downloadCurrPage() throws MalformedURLException {
		return Utility.downloadPage(url, filePath);
	}

	public ArrayList<URL> scanCurrPageOutlinks(String currPage) {
		ArrayList<URL> urls = Utility.getURLs(currPage);

		if (!urls.isEmpty()) {
			this.scanResults.addAll(urls);
		}
		return this.getPageOutlinks();
	}

	public ArrayList<URL> getPageOutlinks() {
		return this.scanResults;
	}

}
