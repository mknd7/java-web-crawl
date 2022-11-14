package web_crawler;

import java.util.ArrayList;
import java.util.Arrays;

// helper class to generate IDs for pages according to hierarchy
// instead of being static, this is coupled with the crawler
// supports numbering only up to 5200 pages as of now

public class PageHierarchy {
	private ArrayList<Character> alphabet;
	private int[] numbers;

	private static final int ALPH = 26;
	private static final int NUM = 200;

	PageHierarchy() {
		alphabet = new ArrayList<>(
				Arrays.asList('A',
						'B', 'C', 'D', 'E', 'F',
						'G', 'H', 'I', 'J', 'K',
						'L', 'M', 'N', 'O', 'P',
						'Q', 'R', 'S', 'T', 'U',
						'V', 'W', 'X', 'Y', 'Z'));

		numbers = new int[NUM];
		Arrays.setAll(numbers, i -> i + 1);
	}

	// page ID for next page
	private String nextPageID = null;

	private Integer alphIndex = 0;
	private Integer numIndex = 0;
	private String alphPart;
	private String numPart;

	public String getNextPageID() {
		if (nextPageID != null) {
			numIndex = (numIndex + 1) % NUM;
			if (numIndex == 0) {
				alphIndex = (alphIndex + 1) % ALPH;
			}
		}
		setNextPageID();
		return nextPageID;
	}

	private void setNextPageID() {
		alphPart = alphabet.get(alphIndex).toString();
		numPart = Integer.toString(numbers[numIndex]);
		nextPageID = alphPart + numPart;
	}

	// Testing
	/*
	 * public static void main(String[] args) {
	 * PageHierarchy p = new PageHierarchy();
	 * for(int i = 0; i < ALPH * NUM; i += 1) {
	 * System.out.println(p.getNextPageID());
	 * }
	 * }
	 */
}
