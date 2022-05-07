package web_crawler;

import java.net.URL;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		Crawler c1 = new Crawler();
		
		/* Test run without using DownloadURL
		String page = Utility.downloadPage(c1.getSeedURL());
		
		ArrayList<URL> urls = Utility.getURLs(page); 
		for(URL url:urls) {
			System.out.println(url.toString());
		}
		*/
	}

}
