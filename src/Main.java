import Models.DocumentPriorityQueue;
import Models.Index;
import Models.SourceData;
import Models.TermFrequencyWeight;
import Services.AsyncCrawler;
import Services.Crawler;
import Services.ICrawler;
import Util.*;
import Util.Vector;

import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.System.exit;

public class Main {
	public static void main(String[] args) throws Exception {
		final Logger LOGGER = Logger.getLogger("SimpleLogger");
		
		ICrawler crawler = new AsyncCrawler(
				Arrays.asList(
						"https://en.wikipedia.org/wiki/List_of_pharaohs",
						"https://en.wikipedia.org/wiki/Pharaoh"),
				10,
				LOGGER
		);
		
		Index index = IndexFactory.build(crawler.getDocs());
		
		
		// ====================== TF-Calculations =========================
		Map<String, List<TermFrequencyWeight>> tfMap = TFCalculator.calculateTF(index.getInvertedIndex());
		
		// ====================== IDF-Calculations =========================
		Map<String, Double> idfMap = IDFCalculator.calculateIDF(index.getInvertedIndex(), crawler.getDocs().size());
		
		// ======================= TF-IDF Calculations =========================
		Map<Integer, Map<String, Double>> vectorMap = Vector.calculateTF_IDF(tfMap, idfMap);
		
		// ======================= UI ======================= //
		Scanner scanner = new Scanner(System.in);
		
		outer: while (true) {
			System.out.println("\033[34mB\033[31mo\033[33mo\033[34mb\033[32ml\033[31me\033[0m");
			System.out.println("\033[36m1. Enter a query\033[0m");
			System.out.println("\033[36m0. Exit\033[0m");
			System.out.print("\033[35mChoose an option: \033[0m");
			Integer choice;
			
			try {
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (Exception e) {
				System.out.println("\033[31mInvalid choice. Please try again using a valid NUMBER.\033[0m");
				System.out.println("-----------------------------------------------------------------------------------");
				scanner.nextLine();
				continue outer;
			}
			
			if (choice.equals(1)) {
				System.out.print("\033[32mEnter your query: \033[0m");
				String query = scanner.nextLine();
				DocumentPriorityQueue rankedDocuments = QueryHandler.handle(query, vectorMap, idfMap);
				System.out.println("\033[33mResults:\033[0m");
				
				final Integer CONSTANT_MAX_COUNT = 10;
				Integer maxCount = CONSTANT_MAX_COUNT;
				
				while (!rankedDocuments.isEmpty() && maxCount != 0) {
					DocumentPriorityQueue.Document currentDocument = rankedDocuments.poll();
					Integer documentId = currentDocument.getDocumentId();
					Double similarity = currentDocument.getSimilarity();
					SourceData documentData = index.getSources().get(documentId);
					System.out.println((CONSTANT_MAX_COUNT - maxCount + 1) + ". Title: " + documentData.getTitle() + "\nURL: " + documentData.getUrl());
					maxCount--;
				}
				
			} else if (choice == 0) {
				System.out.println("Exiting...");
				scanner.close();
				exit(0);
			} else {
				System.out.println("\033[31mInvalid choice. Please try again using a valid NUMBER.\033[0m");
			}
			System.out.println("-----------------------------------------------------------------------------------");
		}
		// ======================= End UI ======================= //
	}
}