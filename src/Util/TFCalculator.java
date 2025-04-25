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
        for (Map.Entry<String, PostingList> entry : invertedIndex.entrySet()) {
            String term = entry.getKey();
            PostingList postingList = entry.getValue();
            Posting current = postingList.getHead();
            while (current != null) {
                Integer DocumentId = current.getDocumentId();
                Integer termFrequency = current.getTermFrequency();
                Double tfValue;
                // Calculate term frequency for the document
                if (termFrequency == 0 || termFrequency == null) {
                    tfValue = 0.0;
                } else {
                    tfValue = 1 + Math.log10(termFrequency);
                }
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
        return tfMap;
    }

    public static void printTF(Map<String, List<TermFrequencyWeight>> tfMap) {
        for (Map.Entry<String, List<TermFrequencyWeight>> entry : tfMap.entrySet()) {
            String term = entry.getKey();
            List<TermFrequencyWeight> termFrequencyObjectList = entry.getValue();
            System.out.println("Term: " + term);
            for (TermFrequencyWeight termFrequencyObject : termFrequencyObjectList) {
                System.out.println("Document ID: " + termFrequencyObject.getIdValue() + ", TF Value: " + termFrequencyObject.getTFValue());
            }
        }
    }

    public static void printTFToFile(Map<String, List<TermFrequencyWeight>> tfMap, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
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
