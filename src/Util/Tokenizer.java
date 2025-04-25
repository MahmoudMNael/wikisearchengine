package Util;

import java.util.Arrays;
import java.util.List;

public abstract class Tokenizer {
	public static String[] tokenize(String text) {
		String[] tokens = text.split("\\W+");
		
		return Arrays.stream(tokens)
		    .filter(token -> !token.isEmpty() && isQueryable(token))
		    .map(String::toLowerCase)
		    .toArray(String[]::new);
	}
	
	private static Boolean isQueryable(String word) {
		List<String> stopWords = List.of("the", "to", "be", "for", "from", "in", "into", "by", "or", "and", "that");
		return word.length() >= 2 && !stopWords.contains(word.toLowerCase());
	}
}
