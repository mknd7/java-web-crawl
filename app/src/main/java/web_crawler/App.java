package web_crawler;

public class App {

	public static void main(String[] args) {
		Crawler c1 = new Crawler("https://www.google.com");
		c1.crawl();
		c1.printCrawlMap();
		c1.printCrawlMetadata();
	}

}
