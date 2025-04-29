package Util;

import Models.Index;
import Models.Posting;
import Models.PostingList;
import Models.SourceData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class IndexFactory {
	
	/**
	 * Builds an inverted index from the given list of sources.
	 * @param sourceData List of SourceData objects to be indexed.
	 * @return An Index object containing the inverted index and source data.
	 */
	public static Index build(List<SourceData> sourceData) {
		Map<Integer, SourceData> sources = new HashMap<>();
		Map<String, PostingList> invertedIndex = new HashMap<>();
		
		// Loop through each source in the list
		for (SourceData data : sourceData) {
			sources.put(data.getSourceId(), data);
			Integer documentLength = 0;
			
			// Split the content of the source into lines and index each line
			for (String contentLine : data.getContent().split("\n")) {
				documentLength += indexOneLineReturnLineLength(invertedIndex, contentLine, data.getSourceId());
			}
			
			sources.get(data.getSourceId()).setContentLength(documentLength);
		}
		
		return new Index(sources, invertedIndex);
	}
	
	/**
	 * Indexes a single line of text and updates the inverted index.
	 * @param invertedIndex The inverted index to update.
	 * @param line The line of text to index.
	 * @param documentId The ID of the document containing the line.
	 * @return The length of the line.
	 */
	private static Integer indexOneLineReturnLineLength(Map<String, PostingList> invertedIndex, String line, Integer documentId) {
		String[] words = Tokenizer.tokenize(line);
		Integer lineLength = words.length;
		
		// Loop through each word in the line
		for (String word : words) {
			if (!invertedIndex.containsKey(word)) {
				invertedIndex.put(word, new PostingList());
			}
			
			if (!invertedIndex.get(word).contains(documentId)) {
				invertedIndex.get(word).incrementDocumentFrequency(1);
				invertedIndex.get(word).addPostingItem(new Posting(documentId));
			} else {
				invertedIndex.get(word).getTail().incrementTermFrequency(1);
			}
			
			invertedIndex.get(word).incrementTermFrequency(1);
		}
		
		return lineLength;
	}
}
