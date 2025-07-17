package main;

import expenses_manager.ExpenseManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ExpenseManager expenseManager = new ExpenseManager();
        int choice;

        System.out.println("\n💸 Welcome to your 'Expense Tracker' System 💸");
        System.out.println("*".repeat(40));

        do {

            System.out.println("\n📗 Main Menu");
            System.out.println("    1. Add a new expense");
            System.out.println("    2. Show all expenses (sorted)");
            System.out.println("    3. Show expenses by category");
            System.out.println("    4. Delete an expense");
            System.out.println("    5. Save expenses");
            System.out.println("    6. Load expenses from file");
            System.out.println("    7. Exit");

            while (true) {
                System.out.print("Enter your choice: ");
                String userInput = input.nextLine().trim();
                if (userInput.isEmpty()) System.out.println("❌ Invalid input.");
                else {
                    try {
                        choice = Integer.parseInt(userInput);
                        if (choice >= 1 && choice <= 7) break;
                        else System.out.println("❌ Invalid input. Please enter a number between 1 and 7.");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input.");
                    }
                }
            }

            switch (choice) {

                case 1:
                    expenseManager.addNewExpense(input);
                    break;

                case 2:
                    expenseManager.showAllExpenses();
                    break;

                case 3:
                    expenseManager.ShowExpensesByCategory(input);
                    break;

                case 4:
                    expenseManager.deleteExpense(input);
                    break;

                case 5:
                    expenseManager.saveExpenses();
                    break;

                case 6:
                    expenseManager.loadFromFile();
                    break;

                case 7:
                    System.out.println("\n👋 Goodbye!");
                    break;

            }

        } while (choice != 7);

        input.close();

    }
}