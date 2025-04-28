package Services;

import Models.SourceData;

import java.util.List;

public interface ICrawler {
	public List<SourceData> getDocs();
}
