package Util;

import Models.TermFrequencyWeight;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Vector {
    // This method calculates the TF-IDF values for each term in the documents
    // and returns a map of document IDs to their corresponding term vectors.
	public static Map<Integer, Map<String, Double>> calculateTF_IDF(Map<String, List<TermFrequencyWeight>> tfMap, Map<String, Double> idfMap) {
		// Create a map to store the TF-IDF values for each document
        Map<Integer, Map<String, Double>> VectorMap = new HashMap<>();
		Double tf_idfValue = 0.0;
		
        // Iterate through the term frequency map
        for (Map.Entry<String, List<TermFrequencyWeight>> entry : tfMap.entrySet()) {
			String term = entry.getKey();
			List<TermFrequencyWeight> tf_IdList = entry.getValue();
			
            // For each term, iterate through the list of TermFrequencyWeight objects
            for (TermFrequencyWeight tf_Id : tf_IdList) {
				Integer documentId = tf_Id.getIdValue();
				Double tfValue = tf_Id.getTFValue();
				Double idfValue = idfMap.getOrDefault(term, 0.0);
				tf_idfValue = tfValue * idfValue;
				
                // Add the TF-IDF value to the document vector map
				if (VectorMap.containsKey(documentId)) {
					Map<String, Double> vector = VectorMap.get(documentId);
					vector.put(term, tf_idfValue);
				} else {
					Map<String, Double> vector = new HashMap<>();
					vector.put(term, tf_idfValue);
					VectorMap.put(documentId, vector);
				}
			}
		}

        // Return the map of document vectors
		return VectorMap;
	}
	
    // This method prints the TF-IDF values for each document in the console
	public static void printVectorMap(Map<Integer, Map<String, Double>> VectorMap) {
		// Iterate through the map of document vectors
        for (Map.Entry<Integer, Map<String, Double>> entry : VectorMap.entrySet()) {
			Integer Id = entry.getKey();
			Map<String, Double> vector = entry.getValue();
			
            // Print the document ID
            System.out.println("Document ID: " + Id);
			
            // Iterate through the term vector and print each term and its TF-IDF value
            for (Map.Entry<String, Double> termEntry : vector.entrySet()) {
				String term = termEntry.getKey();
				Double tf_idfValue = termEntry.getValue();

                // Print the term and its TF-IDF value
				System.out.println("Term: " + term + ", TF-IDF Value: " + tf_idfValue);
			}
			System.out.println("--------------------------------------------------");
		}
	}
	
    // This method prints the TF-IDF values for each document to a file
	public static void printVectorMapToFile(Map<Integer, Map<String, Double>> vectorMap, String filePath) {
		// Create a BufferedWriter to write to the specified file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			// Write a header to the file
            for (Map.Entry<Integer, Map<String, Double>> entry : vectorMap.entrySet()) {
				Integer Id = entry.getKey();
				Map<String, Double> vector = entry.getValue();
				writer.write("Document ID: " + Id);
				writer.newLine();
				// Iterate through the term vector and write each term and its TF-IDF value to the file
                for (Map.Entry<String, Double> termEntry : vector.entrySet()) {
					String term = termEntry.getKey();
					Double tf_idfValue = termEntry.getValue();
					writer.write("Term: " + term + ", TF-IDF Value: " + tf_idfValue);
					writer.newLine();
				}
			}
			writer.write("==========================================================");
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}
}
