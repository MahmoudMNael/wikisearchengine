import Models.Index;
import Services.Crawler;
import Util.*;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
	public static void main(String[] args) throws Exception {
		Crawler crawler = new Crawler(Arrays.asList(
				"https://en.wikipedia.org/wiki/List_of_pharaohs",
				"https://en.wikipedia.org/wiki/Pharaoh"),
				10);
		
		Index index = IndexFactory.build(crawler.getDocs());
		
		// index.printIndex();
		
		// ====================== TF-Calculations =========================
		TFCalculator tfCalc = new TFCalculator();
		tfCalc.calculateTF(index.getInvertedIndex());
		// tfCalc.printTF();
		
		// ====================== IDF-Calculations =========================
		IDFCalculator idfCalc = new IDFCalculator();
		idfCalc.calculateIDF(index.getInvertedIndex(), index.getSources().size());
		// idfCalc.printIDF();
		
		// ======================= TF-IDF Calculations =========================
		DocumentVector docVector = new DocumentVector();
		docVector.calculateTF_IDF(tfCalc.getTfMap(), idfCalc.getIDFMap());
		// docVector.printDocumentVectors();
		
		// ======================= Query Handler ======================= //
		
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			System.out.println("=== Booble ===");
			System.out.println("1. Enter a query");
			System.out.println("0. Exit");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			if (choice == 1) {
				System.out.print("Enter your query: ");
				String query = scanner.nextLine();
				QueryHandler.handle(query);
			} else if (choice == 0) {
				System.out.println("Exiting...");
				scanner.close();
				exit(0);
			} else {
				System.out.println("Invalid choice. Please try again.");
			}
		}
		
		// ======================= End Query Handler ======================= //
	}
}