package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Crawler {

	private String id;
	private String seedURL;
	private CrawledURL startURL;

	private HashMap<String, URLPage> crawlDict;
	private int crawlDepth;

	private static final int maxDepth = 2;
	private static final int maxLinks = 500;
	// private static final int nThreads = 4;

	Crawler(String seedURL) {
		this.id = UUID.randomUUID().toString();
		init(seedURL);
	}

	// can be used to initialize same Crawler again
	public void init(String seedURL) {
		this.crawlDepth = maxDepth;
		this.seedURL = seedURL;
		this.startURL = new CrawledURL(this.getSeedURL(), null);
		this.crawlDict = new HashMap<String, URLPage>();
	}

	public void printCrawlMap() {
		this.startURL.printCrawlMap();
	}

	public void printCrawlMetadata() {
		for (Map.Entry<String, URLPage> entry : crawlDict.entrySet()) {
			System.out.println(entry.getValue().getMetadata());
		}
	}

	public URL getSeedURL() {
		URL seedU = null;
		try {
			seedU = new URL(this.seedURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return seedU;
	}

	public void setSeedURL(String seedURL) {
		this.seedURL = seedURL;
	}

	public void setCrawlDepth(int crawlDepth) {
		if (crawlDepth > maxDepth) {
			System.err.println("Depth cannot be set greater than 4");
			System.exit(-1);
		} else if (crawlDepth < 1) {
			System.err.println("Depth cannot be set lesser than 1");
			System.exit(-1);
		}
		this.crawlDepth = crawlDepth;
	}

	// removes duplicates from next-to-crawl list
	public void removeDuplicates(ArrayList<URL> children) {
		ArrayList<URL> duplicates = new ArrayList<URL>();
		for (URL childurl : children) {
			if (crawlDict.containsKey(childurl.toString())) {
				URLPage urlPage = crawlDict.get(childurl.toString());
				urlPage.setNumInlinks(urlPage.getNumInlinks() + 1);
				duplicates.add(childurl);
			}
		}
		children.removeAll(duplicates);
	}

	public void crawl() {
		System.out.println("Crawler running...");
		crawl(this.startURL, this.crawlDepth);
		System.out.println("");
	}

	public void crawl(CrawledURL curl, int depth) {
		// initialize scanner and download current page
		URLScanner currUrlScanner = new URLScanner(curl.getURL(), curl.getFilePath());
		String currPageContent = null;
		URLPage currURL = null;

		try {
			currPageContent = currUrlScanner.downloadCurrPage();
			currURL = URLPage.createMetadataObject(crawlDict, this.getSeedURL().toString(), curl.toString());
		} catch (MalformedURLException e) {
			System.out.println("Skipping crawl for malformed URL " + curl.toString());
			return;
		}

		// add and store metadata for current page
		currURL.setPageFileSize(Utility.getFileSizeKB(curl.getFilePath()));
		currURL.setNumInlinks(currURL.getNumInlinks() + 1);
		crawlDict.put(curl.toString(), currURL);

		// return if crawl depth is reached
		if (depth == 0) {
			return;
		}

		// scan for outlinks
		ArrayList<URL> children = currUrlScanner.scanCurrPageOutlinks(currPageContent);
		currURL.setNumOutlinks(children.size());
		currURL.setPageCrawled();

		// remove already scanned URLs before subsequent crawls
		this.removeDuplicates(children);
		curl.setChildUrls(children);

		// recursively crawl child URLs
		for (CrawledURL childurl : curl.getChildUrls()) {
			crawl(childurl, depth - 1);
		}
	}

}
