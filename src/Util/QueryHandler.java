package Util;

import Models.DocumentPriorityQueue;
import Models.Index;
import Models.SourceData;
import Models.TermFrequencyWeight;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

public abstract class QueryHandler {
	public static DocumentPriorityQueue handle(String query, Map<Integer, Map<String, Double>> documentsVectors, Map<String, Double> idfMap) {
		String[] tokens = Tokenizer.tokenize(query);
		SourceData sourceData = new SourceData(1, "N/A", "Query", query);
		Index queryIndex = IndexFactory.build(List.of(sourceData));
		
		Map<String, List<TermFrequencyWeight>> tfMap = TFCalculator.calculateTF(queryIndex.getInvertedIndex());
		Map<String, Double> queryVector = Vector.calculateTF_IDF(tfMap, idfMap).get(1);
		
		DocumentPriorityQueue rankedDocuments = new DocumentPriorityQueue();
		
		for (Map.Entry<Integer, Map<String, Double>> entry : documentsVectors.entrySet()) {
			Integer documentId = entry.getKey();
			Map<String, Double> documentVector = entry.getValue();
			
			Double similarity = CosineSimilarityCalculator.calculate(queryVector, documentVector);
			
			rankedDocuments.addDocument(documentId, similarity);
		}
		
		return rankedDocuments;
	}
}
