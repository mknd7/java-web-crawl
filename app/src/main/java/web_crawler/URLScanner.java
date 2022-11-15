package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class URLScanner {

	private URL url;
	private String filePath;

	URLScanner(URL url, String filePath) {
		this.url = url;
		this.filePath = filePath;
	}

	public String downloadCurrPage() throws MalformedURLException {
		return Utility.downloadPage(url, filePath);
	}

	public Set<URL> scanCurrPageOutlinks(String currPage) {
		Set<URL> scanResults = new HashSet<URL>();
		Set<URL> urls = Utility.getURLs(currPage);

		if (!urls.isEmpty()) {
			scanResults.addAll(urls);
		}
		return scanResults;
	}

}
