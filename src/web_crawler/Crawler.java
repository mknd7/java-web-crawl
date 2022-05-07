package web_crawler;

import java.util.UUID;

public class Crawler {
	
	private String id;
	private String seedURL;
	
	private final int maxURLLinks = 200;
	private final int maxTotal = 1000;
	
	Crawler() {
		this.id = UUID.randomUUID().toString();
		this.seedURL = "https://www.google.com";
	}
	
	Crawler(String seedURL) {
		this();
		this.seedURL = seedURL;
	}
	
	public String getSeedURL() {
		return this.seedURL;
	}

}
