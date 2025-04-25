package Util;

import Models.TermFrequencyWeight;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Vector {
    public static Map<Integer, Map<String, Double>> calculateTF_IDF(Map<String, List<TermFrequencyWeight>> tfMap,
            Map<String, Double> idfMap) {
        Map<Integer, Map<String, Double>> VectorMap = new HashMap<>();
        Double tf_idfValue = 0.0;
        for (Map.Entry<String, List<TermFrequencyWeight>> entry : tfMap.entrySet()) {
            String term = entry.getKey();
            List<TermFrequencyWeight> tf_IdList = entry.getValue();
            for (TermFrequencyWeight tf_Id : tf_IdList) {
                Integer documentId = tf_Id.getIdValue();
                Double tfValue = tf_Id.getTFValue();
                if (idfMap.containsKey(term)) {
                    Double idfValue = idfMap.get(term);
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
        }
        return VectorMap;
    }

    public static void printVectorMap(Map<Integer, Map<String, Double>> VectorMap) {
        for (Map.Entry<Integer, Map<String, Double>> entry : VectorMap.entrySet()) {
            Integer Id = entry.getKey();
            Map<String, Double> vector = entry.getValue();
            System.out.println("Document ID: " + Id);
            for (Map.Entry<String, Double> termEntry : vector.entrySet()) {
                String term = termEntry.getKey();
                Double tf_idfValue = termEntry.getValue();
                System.out.println("Term: " + term + ", TF-IDF Value: " + tf_idfValue);
            }
            System.out.println("--------------------------------------------------");
        }
    }

    public static void printVectorMapToFile(Map<Integer, Map<String, Double>> vectorMap, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Integer, Map<String, Double>> entry : vectorMap.entrySet()) {
                Integer Id = entry.getKey();
                Map<String, Double> vector = entry.getValue();
                writer.write("Document ID: " + Id);
                writer.newLine();
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
