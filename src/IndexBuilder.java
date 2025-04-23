import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexBuilder {
	private Map<Integer, SourceData> sources;
	private Map<String, PostingList> invertedIndex;
	
	public IndexBuilder() {
		this.sources = new HashMap<>();
		this.invertedIndex = new HashMap<>();
	}
	
	public void buildIndex(List<SourceData> sourceData) {
		for (SourceData data : sourceData) {
			sources.put(data.getSourceId(), data);
			Integer documentLength = 0;
			for (String contentLine : data.getContent().split("\n")) {
				documentLength += indexOneLineReturnLineLength(contentLine, data.getSourceId());
			}
			sources.get(data.getSourceId()).setContentLength(documentLength);
		}
	}
	
	public Integer indexOneLineReturnLineLength(String line, Integer documentId) {
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
	
	public void printIndex() {
		System.out.println(invertedIndex.size());
		for (Map.Entry<String, PostingList> entry : invertedIndex.entrySet()) {
			System.out.println("Term: " + entry.getKey());
			entry.getValue().printPostingList();
		}
	}
	
	private Boolean isQueryable(String word) {
		List<String> stopWords = List.of("the", "to", "be", "for", "from", "in", "into", "by", "or", "and", "that");
		return word.length() >= 2 && !stopWords.contains(word.toLowerCase());
	}
}
