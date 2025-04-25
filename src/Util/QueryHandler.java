package Util;

import Models.Index;
import Models.SourceData;

import java.util.List;

public abstract class QueryHandler {
	public static void handle(String query) {
		String[] tokens = Tokenizer.tokenize(query);
		SourceData sourceData = new SourceData(1, "N/A", "Query", query);
		Index queryIndex = IndexFactory.build(List.of(sourceData));
		TFCalculator calculator = new TFCalculator();
		calculator.calculateTF(queryIndex.getInvertedIndex());
		calculator.printTF();
	}
}
