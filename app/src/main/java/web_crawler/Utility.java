package web_crawler;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.regex.*;

// comprises static helper functions
public class Utility {

	public static class Pair<T1, T2> {
		public final T1 fst;
		public final T2 snd;

		public Pair(T1 fst, T2 snd) {
			this.fst = fst;
			this.snd = snd;
		}
	}

	public static String downloadPage(URL url, String filePath) throws MalformedURLException {
		String line = null, page = null;

		try {
			prepareDirectories(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

			line = br.readLine();
			page = "";
			while (line != null) {
				bw.write(line + '\n');
				page += line + '\n';
				line = br.readLine();
			}

			br.close();
			bw.close();

		} catch (IOException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unexpected error while downloading page from URL");
			e.printStackTrace();
		}

		return page;
	}

	// make parent directories if they do not exist
	public static void prepareDirectories(String filePath) {
		File myFile = new File(filePath);
		File parentDir = myFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
	}

	public static Set<URL> getURLs(String page) {
		Set<String> links = new HashSet<String>();
		Set<URL> linkURLs = new HashSet<URL>();

		// for responses like HTTP 500
		if (page == null) {
			return linkURLs;
		}

		Pattern linkPattern = Pattern.compile("<a.+?\\R*href\\R*=\\R*[\"\']?([^\"\'>]+)[\"\']?",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher pageMatcher = linkPattern.matcher(page);
		String link, currUrlString;

		while (pageMatcher.find()) {
			link = pageMatcher.group(1);
			links.add(link);
			currUrlString = link;

			// any local links without the protocol
			if (currUrlString.indexOf("//") == 0) {
				currUrlString = "https:" + currUrlString;
			} else if (currUrlString.indexOf("http") == -1) {
				continue;
			}

			try {
				// remove query params before adding to set
				if (currUrlString.indexOf("?") != -1) {
					currUrlString = currUrlString.substring(0, currUrlString.indexOf("?"));
				}
				URL currUrl = new URL(currUrlString);
				linkURLs.add(currUrl);
			} catch (MalformedURLException e) {
				// e.printStackTrace();
			}
		}

		return linkURLs;
	}

	public static String[] getLines(String page) {
		String[] lines = page.split("\\R+", -1);
		return lines;
	}

	// return a filename-safe String from URL
	public static String getSanitizedFilename(URL url) {
		// only allow numbers, letters or underscore
		return url.toString().replaceAll("\\W+", "");
	}

	// return size in bytes, or -1 if not found
	public static long getFileSize(String filePath) {
		Path path = Paths.get(filePath);
		long size = -1;
		try {
			size = Files.size(path);
		} catch (IOException e) {
			// ignore if file not found
		}
		return size;
	}

	// get size and return formatted String size in KB
	public static String getFormattedSize(String filePath) {
		long fileSize = getFileSize(filePath);
		if (fileSize == -1) {
			return null;
		}
		return String.format("%.2f KB", fileSize / 1024.0);
	}

	// return formatted String size in KB
	public static String getFormattedSize(long fileSize) {
		if (fileSize == -1) {
			return null;
		}
		return String.format("%.2f KB", fileSize / 1024.0);
	}

}
