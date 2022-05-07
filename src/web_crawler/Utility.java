package web_crawler;

import java.io.*;
import java.util.ArrayList;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.regex.*;

// comprises of static helper functions
public class Utility {
	
	public static String downloadPage(String pageURL) {
		URL url = null;
		String page = null;
		
		try {
			url = new URL(pageURL);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line = br.readLine();
			page = "";
			while(line != null) {
				page += line + '\n';
				line = br.readLine();
			}
			
			br.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unexpected error while downloading page from URL");
			e.printStackTrace();
		}
		
		return page;
	}
	
	public static ArrayList<URL> getURLs(String page) {
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<URL> linkURLs = new ArrayList<URL>();
		
		Pattern linkPattern = Pattern.compile("<a.+?\\R*href\\R*=\\R*[\"\']?([^\"\'>]+)[\"\']?", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher pageMatcher = linkPattern.matcher(page);
		
		while(pageMatcher.find()){
		    String link = pageMatcher.group(1);
		    links.add(link);
	    	String last = links.get(links.size() - 1);
	    	if(last.indexOf("http") == -1 && last.indexOf("//") == 0) {
	    		last = "https:" + last;
	    	} else if(last.indexOf("http") == -1) {
	    		continue;
	    	}
	    	
	    	// add URLs to list
		    try {
		    	linkURLs.add(new URL(last));
		    } catch(MalformedURLException e) {
		    	e.printStackTrace();
		    }
		}
		
		return linkURLs;
	}
	
	public static String[] getLines(String page) {
		String[] lines = page.split("\\R+", -1);
		return lines;
	}
}
