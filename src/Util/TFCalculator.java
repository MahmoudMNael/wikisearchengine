package Util;

import Models.TermFrequencyWeight;
import Models.Posting;
import Models.PostingList;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class TFCalculator {
    public static Map<String, List<TermFrequencyWeight>> calculateTF(Map<String, PostingList> invertedIndex) {
        // Iterate through the inverted index to calculate term frequency for each term
        Map<String, List<TermFrequencyWeight>> tfMap = new HashMap<>();
        
        // Iterate through the inverted index and calculate term frequency for each term
        for (Map.Entry<String, PostingList> entry : invertedIndex.entrySet()) {
            String term = entry.getKey();
            PostingList postingList = entry.getValue();
            Posting current = postingList.getHead();
            
            // Iterate through the postings in the posting list
            while (current != null) {
                Integer DocumentId = current.getDocumentId();
                Integer termFrequency = current.getTermFrequency();
                Double tfValue;
                
                // Calculate the term frequency value using the formula: TF = 1 + log10(TF)
                // If termFrequency is 0 or null, set tfValue to 0.0
                if (termFrequency == 0 || termFrequency == null) {
                    tfValue = 0.0;
                } else {
                    tfValue = 1 + Math.log10(termFrequency);
                }

                // Create a TermFrequencyWeight object with the document ID and term frequency value
                TermFrequencyWeight termFrequencyObject = new TermFrequencyWeight(DocumentId, tfValue);
                
                // Add the TF_DocID object to the map using if-else
                if (tfMap.containsKey(term)) {
                    tfMap.get(term).add(termFrequencyObject);
                } else {
                    List<TermFrequencyWeight> termFrequencyObjectList = new ArrayList<>();
                    termFrequencyObjectList.add(termFrequencyObject);
                    tfMap.put(term, termFrequencyObjectList);
                }

                // Move to the next posting in the list
                current = current.getNext();
            }
        }
        // Return the map of term frequency objects
        return tfMap;
    }

    // This method prints the term frequency map to the console
    public static void printTF(Map<String, List<TermFrequencyWeight>> tfMap) {
        // Iterate through the term frequency map and print each term and its corresponding term frequency objects
        for (Map.Entry<String, List<TermFrequencyWeight>> entry : tfMap.entrySet()) {
            String term = entry.getKey();
            List<TermFrequencyWeight> termFrequencyObjectList = entry.getValue();
            System.out.println("Term: " + term);
            // Iterate through the list of TermFrequencyWeight objects and print each document ID and its term frequency value
            for (TermFrequencyWeight termFrequencyObject : termFrequencyObjectList) {
                System.out.println("Document ID: " + termFrequencyObject.getIdValue() + ", TF Value: " + termFrequencyObject.getTFValue());
            }
        }
    }

    // This method prints the term frequency map to a file
    public static void printTFToFile(Map<String, List<TermFrequencyWeight>> tfMap, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Iterate through the term frequency map and write each term and its corresponding term frequency objects to the file
            for (Map.Entry<String, List<TermFrequencyWeight>> entry : tfMap.entrySet()) {
                String term = entry.getKey();
                List<TermFrequencyWeight> termFrequencyObjectList = entry.getValue();
                writer.write("Term: " + term + "\n");
                for (TermFrequencyWeight termFrequencyObject : termFrequencyObjectList) {
                    writer.write("Document ID: " + termFrequencyObject.getIdValue() + ", TF Value: " + termFrequencyObject.getTFValue() + "\n");
                }
                writer.write("==========================================================\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
