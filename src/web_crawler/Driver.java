package web_crawler;

public class Driver {

	public static void main(String[] args) {
		Crawler c1 = new Crawler();
		c1.crawl(null);
		c1.printCrawlMap();
	}

}
