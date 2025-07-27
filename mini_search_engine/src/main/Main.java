package main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import service.SearchEngine;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        SearchEngine searchEngine = new SearchEngine();
        int option;

        System.out.println("\n" + "=".repeat(40) + "\n🔍 Welcome to the Mini Search Engine! 🔍\n" + "=".repeat(40));
        System.out.println();
        System.out.println("\n📘 This program allows you to search for any word\ninside a text file and displays all the lines\nwhere the word appears.");
        System.out.println("\n💡 Bonus: It also tells you which word appears\nthe most in the entire file!\n");
        System.out.println("\nLet's get started...");

        // Loading File
        File file = null;
        try {
            file = loadingFile(input);
        } catch (IOException e) {
            System.out.println("❌ " + e.getMessage());
        }

        do {

            //choose option
            System.out.println("\nOption available:");
            System.out.println("    1️⃣ Search for a keyword");
            System.out.println("    2️⃣ Show the most frequent word in the file");
            System.out.println("    3️⃣ Exist");
            while (true) {
                System.out.print("\n> Your choice: ");
                String userInput = input.nextLine().toLowerCase();
                if (userInput.isEmpty()) {
                    System.out.println("❌ Your choice cannot be empty.");
                    continue;
                }
                try {
                    option = Integer.parseInt(userInput);

                    if (option >= 1 && option <= 3) break;
                    System.out.println("❌ Invalid input. Enter a number between 1 and 3.");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input.");
                }
            }


            switch (option) {

                case 1:
                    try {
                        searchEngine.searchForKeyword(file, input);
                    } catch (IOException e) {
                        System.out.println("\n❌ " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        searchEngine.mostFrequentWord(file);
                    } catch (IOException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;

                case 3:
                    break;
            }

        } while (option != 3);

        System.out.println("\n👋 Thank for testing! See you next time...");
        input.close();

    }

    // Loading file
    public static File loadingFile (Scanner input) throws IOException {

        while (true) {

            System.out.print("\n" + "=".repeat(52) + "\n📂 Please enter the file name (the path of the file): ");
            String path = input.nextLine().trim();
            if (path.isEmpty()) {
                System.out.println("❌ File name cannot be empty.");
                continue;
            }

            File file = new File(path);
            File parent = file.getParentFile();
            if (parent == null || !parent.exists()) {
                System.out.println("❌ This path of file doesn't exist.");
                continue;
            }
            if (file.isDirectory()) {
                System.out.println("❌ This is the path of a directory. Enter the path of a file.");
                continue;
            }
            System.out.println("✅ File loaded successfully!");
            return file;
        }

    }

}