package main;

import service.PlanningManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PlanningManager planningManager = new PlanningManager();
        int choice;

        System.out.println("\n" + "=".repeat(41));
        System.out.println("📅 Welcome to the Weekly Schedule Manager");
        System.out.println("=".repeat(41));

        do {

            System.out.println("\n Main Menu:");
            System.out.println("    1️⃣. Load Data from File");
            System.out.println("    2️⃣. Add a Task");
            System.out.println("    3️⃣. Show Tasks for a Specific Day");
            System.out.println("    4️⃣. Show Weekly Schedule");
            System.out.println("    5️⃣. Save to File");
            System.out.println("    6️⃣. Exit");

            while (true) {
                System.out.print("\nPlease enter your choice (1-6): ");
                String useInput = input.nextLine().trim();
                if (useInput.isEmpty()) {
                    System.out.println("❌ Your choice cannot be empty");
                    continue;
                }
                try {
                    choice = Integer.parseInt(useInput);
                    if (choice >= 1 && choice <= 6) break;
                    System.out.println("❌ Invalid input. Enter a number between 1 and 6.");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input.");
                }
            }

            switch (choice) {

                case 1:
                    planningManager.loadData();
                    break;

                case 2:
                    planningManager.addTask(input);
                    break;

                case 3:
                    planningManager.showTasksDay(input);
                    break;

                case 4:
                    planningManager.showWeeklySchedule();
                    break;

                case 5:
                    planningManager.saveToFile();
                    break;

                case 6:
                    System.out.println("\n👋 Exiting the program... Goodbye!");
                    break;
            }

        } while (choice != 6);

        input.close();

    }
}