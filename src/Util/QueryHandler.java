package Util;

import Models.Index;
import Models.SourceData;

import java.util.List;
import java.util.Arrays;

public abstract class QueryHandler {
	public static void handle(String query) {
		String[] tokens = Tokenizer.tokenize(query);
		SourceData sourceData = new SourceData(1, "N/A", "Query", query);
		Index queryIndex = IndexFactory.build(Arrays.asList(sourceData));
	}
}
