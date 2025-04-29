package Util;

import Models.PostingList;

import java.util.Map;
import java.util.HashMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class IDFCalculator {
	// Note about the IDF calculation:
	// IDF is calculated using the formula: IDF = log10(totalDocuments / documentFrequency)
	// The higher the IDF value, the more important the term is in the document collection.
	// A term that appears in many documents will have a lower IDF value,
	// while a term that appears in fewer documents will have a higher IDF value.
	public static Map<String, Double> calculateIDF(Map<String, PostingList> invertedIndex, Integer totalDocuments) {
        Map <String, Double> idfMap = new HashMap<>();
		// Iterate through the inverted index to calculate IDF for each term
		for (Map.Entry<String, PostingList> entry : invertedIndex.entrySet()) {
			String term = entry.getKey();
			PostingList postingList = entry.getValue();
			Integer documentFrequency = postingList.getDocumentFrequency(); // Get the document frequency for the term
			Double idfValue;
			// Calculate IDF using the formula: IDF = log10(totalDocuments / documentFrequency)
			if (documentFrequency == 0 || documentFrequency == null) {
				idfValue = 0.0;
			} else {
				idfValue = Math.log10((double) totalDocuments / documentFrequency);
			}
			// Store the IDF value in the idfMap
			idfMap.put(term, idfValue);
		}
        return idfMap;
	}

	// This method prints the IDF values for each term in the console
	public static void printIDF(Map<String, Double> idMap) {
		for (Map.Entry<String, Double> entry : idMap.entrySet()) {
			String term = entry.getKey();
			Double idfValue = entry.getValue();
			System.out.println("Term: " + term + ", IDF Value: " + idfValue);
		}
	}

    // This method prints the IDF values for each term to a file
    public static void printIDFToFile(Map<String, Double> idfMap, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Double> entry : idfMap.entrySet()) {
                String term = entry.getKey();
                Double idfValue = entry.getValue();
                writer.write("Term: " + term + ", IDF Value: " + idfValue);
                writer.newLine();
            }
            writer.write("==========================================================");
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
