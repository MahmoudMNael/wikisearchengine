import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
		Crawler crawler = new Crawler(Arrays.asList(
				"https://en.wikipedia.org/wiki/List_of_pharaohs",
				"https://en.wikipedia.org/wiki/Pharaoh"),
				10);
		
		List<SourceData> sourceDataList = crawler.getDocs();
		
		IndexBuilder indexBuilder = new IndexBuilder();
		
		indexBuilder.buildIndex(sourceDataList);
		
		indexBuilder.printIndex();
	}
}