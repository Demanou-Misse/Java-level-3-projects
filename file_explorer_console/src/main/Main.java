package main;

import java.io.IOException;
import java.util.Scanner;
import service.FileExplorer;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        FileExplorer fileExplorer = new FileExplorer();
        int choice;

        System.out.println("\n📁 Welcome to the Java File Explorer!");

        do {

            System.out.println("\nPlease choose an option:");
            System.out.println("    1. List directory contents");
            System.out.println("    2. Create a file or folder");
            System.out.println("    3. Delete a file or folder");
            System.out.println("    4. Search for a file by name");
            System.out.println("    5. Exit");

            while (true) {
                System.out.print("\n> Your choice: ");
                String userInput = input.nextLine().trim();
                if (userInput.isEmpty()) {
                    System.out.println("❌ Your choice cannot be empty.");
                    continue;
                }
                try {
                    choice = Integer.parseInt(userInput);

                    if (choice >= 1 && choice <= 5) break;
                    System.out.println("❌ Invalid input. Please make sure to choose a number between 1 and 5.");
                }catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input.");
                }
            }

            switch (choice) {

                case 1:
                    fileExplorer.listDirectoryContents(input);
                    break;

                case 2:
                    try {
                        fileExplorer.createFileOrFolder(input);
                    } catch (IOException e) {
                        System.out.println("\n❌ " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        fileExplorer.deleteFileOrFolder(input);
                    } catch (IOException e) {
                        System.out.println("\n❌ " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        fileExplorer.searchFile(input);
                    } catch (IOException e) {
                        System.out.println("\n❌ " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("\n👋 Exiting the File Explorer. Goodbye!");
                    break;
            }

        } while (choice != 5);

        input.close();

    }

}