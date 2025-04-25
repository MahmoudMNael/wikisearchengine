package Util;

import java.util.Map;

public abstract class CosineSimilarityCalculator {
	public static Double calculate(Map<String, Double> queryVector, Map<String, Double> documentVector) {
		Double dotProduct = 0.0;
		Double queryMagnitude = 0.0;
		Double documentMagnitude = 0.0;
		
		for (String term : queryVector.keySet()) {
			Double queryValue = queryVector.getOrDefault(term, 0.0);
			Double documentValue = documentVector.getOrDefault(term, 0.0);
			
			dotProduct += queryValue * documentValue;
			queryMagnitude += queryValue * queryValue;
		}
		
		for (Double value : documentVector.values()) {
			documentMagnitude += value * value;
		}
		
		if (queryMagnitude == 0 || documentMagnitude == 0) {
			return 0.0;
		}
		
		return dotProduct / (Math.sqrt(queryMagnitude) * Math.sqrt(documentMagnitude));
	}
}