import java.util.HashMap;
import java.util.Map;

public class IndexBuilder {
	private Map<Integer, SourceData> sources;
	private Map<String, PostingList> invertedIndex;
	
	public IndexBuilder() {
		this.sources = new HashMap<>();
		this.invertedIndex = new HashMap<>();
	}
	
	public void buildIndex(SourceData[] sourceData) {
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
	
	private Boolean isQueryable(String word) {
		return word.length() >= 2 && !word.equalsIgnoreCase("the") && !word.equalsIgnoreCase("to") && !word.equalsIgnoreCase("be") && !word.equalsIgnoreCase("for") && !word.equalsIgnoreCase("from") && !word.equalsIgnoreCase("in")
				&& !word.equalsIgnoreCase("into") && !word.equalsIgnoreCase("by") && !word.equalsIgnoreCase("or") && !word.equalsIgnoreCase("and") && !word.equalsIgnoreCase("that");
	}
}
