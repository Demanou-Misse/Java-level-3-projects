
package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchEngine {

    // Search for a keyword
    public void searchForKeyword(File file, Scanner input) throws IOException {
        String keyword;

        while (true) {
            System.out.print("\n🔎 Enter the keyword to search: ");
            keyword = input.nextLine().trim();
            if (keyword.isEmpty()) {
                System.out.println("❌ Keyword cannot be empty");
                continue;
            }
            break;
        }
        System.out.println("\n" + "-".repeat(30) + "\n🔎 Search Results for: \"" + keyword + "\"\n" + "-".repeat(30) + "\n");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 1;
            int j = 0;

                while ((line = (br.readLine())) != null) {
                    line = line.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                    String[] words = line.split("\\s+");

                    for (String word : words) {
                        if (!word.isEmpty() && word.equals(keyword.toLowerCase())) {
                            System.out.println("Line " + i + ": " + line);
                            j++;
                            break;
                        }
                    }
                    i++;
                }
                System.out.println("\n✅ Total occurrences of \"" + keyword + "\": " + j);

        } catch (IOException e) {
                System.out.println("\n❌ " + e.getMessage());
        }
    }

    // The most frequent word
    public void mostFrequentWord (File file) throws IOException {
        Map<String, Integer> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                String[] words = line.split("\\s+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        map.put(word, map.getOrDefault(word, 0) + 1);
                    }
                }
            }

            String mfw = null;
            int max = 0;

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    mfw = entry.getKey();
                }
            }

            System.out.println("\n" + "-".repeat(30) + "\n📊 Most Frequent Word in File\n" + "-".repeat(30) + "\n");
            System.out.println("\"" + mfw + "\" → " + max + " times\n");
            System.out.println("=".repeat(32) + "\n✅ Search completed successfully.\n" + "=".repeat(32));

        } catch (IOException e) {
            System.out.println("\n❌ " + e.getMessage());
        }

    }

}
