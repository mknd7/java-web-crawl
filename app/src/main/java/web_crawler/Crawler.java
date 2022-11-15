package web_crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Crawler {

	private String id;
	private String seedURL;
	private CrawledURL startURL;

	private HashMap<String, URLMetadata> crawlDict;
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
		this.crawlDict = new HashMap<String, URLMetadata>();
	}

	public void printCrawlMap(Boolean withMetadata) {
		this.startURL.printCrawlMap(withMetadata);
	}

	public void printCrawlMetadata() {
		for (Map.Entry<String, URLMetadata> entry : crawlDict.entrySet()) {
			System.out.println(entry.getValue().getMetadata());
		}
	}

	public URL getSeedURL() {
		URL seedURL = null;
		try {
			seedURL = new URL(this.seedURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return seedURL;
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

	public void crawl() {
		System.out.println("Crawler running...");
		crawl(this.crawlDepth);
		System.out.println("");
	}

	public void crawl(int maxDepth) {
		ArrayDeque<Utility.Pair<CrawledURL, Integer>> nextToCrawl = new ArrayDeque<>();
		nextToCrawl.offer(new Utility.Pair<CrawledURL, Integer>(this.startURL, maxDepth));

		while (!nextToCrawl.isEmpty()) {
			Utility.Pair<CrawledURL, Integer> urlWithDepthPair = nextToCrawl.poll();
			CrawledURL curl = urlWithDepthPair.fst;
			int depth = urlWithDepthPair.snd;

			// initialize scanner and download current page
			URLScanner currUrlScanner = new URLScanner(curl.getURL(), curl.getFilePath());
			String currPageContent = null;
			URLMetadata currURL = null;

			try {
				currPageContent = currUrlScanner.downloadCurrPage();
				currURL = URLMetadata.createMetadataObject(crawlDict, this.getSeedURL().toString(), curl.toString());
			} catch (MalformedURLException e) {
				System.out.println("Skipping crawl for malformed URL " + curl.toString());
				continue;
			}

			// add and store metadata for current page
			currURL.setPageFileSize(Utility.getFormattedSize(curl.getFilePath()));
			currURL.setNumInlinks(currURL.getNumInlinks() + 1);
			crawlDict.put(curl.toString(), currURL);
			curl.setUrlMetadata(currURL);

			// continue crawl only if not leaf URL
			if (depth > 0) {
				// scan for outlinks
				Set<URL> children = currUrlScanner.scanCurrPageOutlinks(currPageContent);
				currURL.setNumOutlinks(children.size());
				currURL.setPageCrawled();

				// remove already scanned URLs before subsequent crawls
				this.removeDuplicates(children);
				curl.setChildUrls(children);

				// add all child URLs to queue
				for (CrawledURL childurl : curl.getChildUrls()) {
					nextToCrawl.offer(new Utility.Pair<CrawledURL, Integer>(childurl, depth - 1));
				}
			}
		}
	}

	// removes links already present in crawlDict from next-to-crawl list
	public void removeDuplicates(Set<URL> children) {
		Set<URL> duplicates = new HashSet<URL>();
		for (URL childurl : children) {
			if (crawlDict.containsKey(childurl.toString())) {
				URLMetadata urlPage = crawlDict.get(childurl.toString());
				urlPage.setNumInlinks(urlPage.getNumInlinks() + 1);
				duplicates.add(childurl);
			}
		}
		children.removeAll(duplicates);
	}

}
