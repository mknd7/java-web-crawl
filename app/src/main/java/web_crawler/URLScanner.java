package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class URLScanner {

	private URL url;
	private String filePath;
	private Set<URL> scanResults;

	URLScanner(URL url, String filePath) {
		this.scanResults = new HashSet<URL>();
		this.url = url;
		this.filePath = filePath;
	}

	public String downloadCurrPage() throws MalformedURLException {
		return Utility.downloadPage(url, filePath);
	}

	public Set<URL> scanCurrPageOutlinks(String currPage) {
		Set<URL> urls = Utility.getURLs(currPage);

		if (!urls.isEmpty()) {
			this.scanResults.addAll(urls);
		}
		return this.getPageOutlinks();
	}

	public Set<URL> getPageOutlinks() {
		return this.scanResults;
	}

}
