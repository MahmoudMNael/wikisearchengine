import java.util.HashMap;
import java.util.Map;

public class Index {
	private Map<Integer, SourceData> sources;
	private Map<String, PostingList> invertedIndex;
	
	public Index(Map<Integer, SourceData> sources,
							 Map<String, PostingList> invertedIndex) {
		this.sources = sources;
		this.invertedIndex = invertedIndex;
	}
	
	public void printIndex() {
		System.out.println(invertedIndex.size());
		for (Map.Entry<String, PostingList> entry : invertedIndex.entrySet()) {
			System.out.println("Term: " + entry.getKey());
			entry.getValue().printPostingList();
		}
	}
	
	public Map<String, PostingList> getInvertedIndex() {
		return invertedIndex;
	}
	
	public Map<Integer, SourceData> getSources() {
		return sources;
	}
}
