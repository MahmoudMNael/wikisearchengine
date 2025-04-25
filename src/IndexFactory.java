import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class IndexFactory {
	public static Index buildIndex(List<SourceData> sourceData) {
		Map<Integer, SourceData> sources = new HashMap<>();
		Map<String, PostingList> invertedIndex = new HashMap<>();
		
		for (SourceData data : sourceData) {
			sources.put(data.getSourceId(), data);
			Integer documentLength = 0;
			for (String contentLine : data.getContent().split("\n")) {
				documentLength += indexOneLineReturnLineLength(invertedIndex, contentLine, data.getSourceId());
			}
			sources.get(data.getSourceId()).setContentLength(documentLength);
		}
		
		return new Index(sources, invertedIndex);
	}
	
	private static Integer indexOneLineReturnLineLength(Map<String, PostingList> invertedIndex, String line, Integer documentId) {
		String[] words = line.split("\\W+");
		Integer lineLength = words.length;
		
		for (String word : words) {
			if (!isQueryable(word)) {
				continue;
			}
			
			word = word.toLowerCase();
			
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
	
	private static Boolean isQueryable(String word) {
		List<String> stopWords = List.of("the", "to", "be", "for", "from", "in", "into", "by", "or", "and", "that");
		return word.length() >= 2 && !stopWords.contains(word.toLowerCase());
	}
}
