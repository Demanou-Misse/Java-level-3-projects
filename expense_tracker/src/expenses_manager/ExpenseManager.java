package expenses_manager;

import expense.Expense;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ExpenseManager {

    private LocalDate date;
    private Map<LocalDate, ArrayList<Expense>> expenses = new TreeMap<>();

    public LocalDate validDate(Scanner input) {
        LocalDate today = LocalDate.now();
        while (true) {
            System.out.print("\nEnter the date (YYYY-MM-DD), or press Enter for today's date [ " + today + " ]: ");
            String userInput = input.nextLine().trim();

            if (userInput.isEmpty()) {
                System.out.println("  Date: " + today);
                return today;
            }

            try {
                LocalDate date = LocalDate.parse(userInput);

                if (date.isAfter(today)) {
                    System.out.println("❌ The date cannot be in the future.");
                } else {
                    return date;
                }
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid format. Please use YYYY-MM-DD (e.g., 2025-07-16).");
            }

        }
    }

    // Add a new Expense
    public void addNewExpense (Scanner input) {

        date = validDate(input);

        while (true) {
            System.out.print("Enter the category: ");
            String category = input.nextLine().trim();

            if (category.isEmpty()) System.out.println("❌ Category cannot be empty.");
            else {

                double amount;
                while (true) {
                    System.out.print("Enter the amount: ");
                    String userInput = input.nextLine().trim();
                    if (userInput.isEmpty()) System.out.println("❌ Amount cannot be empty.");
                    else {
                        try {
                            amount = Double.parseDouble(userInput);
                            if (amount <= 0) System.out.println("❌ Enter an amount greater than '0'.");
                            else break;
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Invalid input.");
                        }
                    }
                }

                System.out.print("Enter a short description, or press Enter if you don't need a description: ");
                String description = input.nextLine().trim();
                if (description.isEmpty()) {
                    description = "none";
                    System.out.println("  Description: " + description);

                }

                Expense expense = new Expense(category, amount, description);
                expenses.putIfAbsent(date, new ArrayList<>());
                expenses.get(date).add(expense);
                System.out.println("\n✅ Expense added successfully!");
                break;
            }
        }

    }

    // Show all Expenses
    public void showAllExpenses() {
        if (expenses.isEmpty()) System.out.println("\n❌ No expense found.");
        else {
            System.out.println("\n📅 All expenses (sorted by date):");
            double total = 0.0;

            for (Map.Entry<LocalDate, ArrayList<Expense>> entry : expenses.entrySet()) {
                LocalDate date = entry.getKey();
                ArrayList<Expense> expense = entry.getValue();
                System.out.println("\n🎯 Date: [" + date + "]");

                for (Expense expense1 : expense) {
                    total += expense1.getAmount();
                    System.out.println("  " + expense1);
                }
            }

            System.out.println("\n" + "_".repeat(40));
            System.out.println("Total: " + total + " £");
        }
    }

    // Show expenses by Category
    public void ShowExpensesByCategory (Scanner input) {
        if (expenses.isEmpty()) System.out.println("\n❌ No expense found.");
        else {
            while (true) {
                System.out.print("\n👉Enter category to filter: ");
                String category = input.nextLine().trim();
                if (category.isEmpty()) System.out.println("❌ Invalid input. Category cannot be empty.");
                else {
                    double total = 0.0;
                    for (Map.Entry<LocalDate, ArrayList<Expense>> entry : expenses.entrySet()) {
                        ArrayList<Expense> expenses1 = entry.getValue();
                        for (Expense expense : expenses1) {
                            if (category.equalsIgnoreCase(expense.getCategory())) {
                                total += expense.getAmount();
                                System.out.println(" Date: " + date + "    | Amount: " + expense.getAmount() + "    | Description: " + expense.getDescription());
                            }
                        }
                    }

                    if (total == 0) {
                        System.out.println("❌ This category do not exist.");
                    } else {
                        System.out.println("*".repeat(50));
                        System.out.println("Total for " + category + " category : " + total + " £");
                        System.out.println("*".repeat(50));
                        break;
                    }

                }

            }
        }

    }

    // Delete an expense
    public void deleteExpense(Scanner input) {
        if (expenses.isEmpty()) {
            System.out.println("\n❌ No expense found.");
        } else {
            LocalDate date = validDate(input);
            if (!expenses.containsKey(date)) System.out.println("❌ This date do not exist in your expenses.");
            else {
                int count = 1;
                System.out.println("\n🎯 Expenses on " + date + ":");
                ArrayList<Expense> expenses1 = expenses.get(date);
                for (int i = 0; i < expenses1.size(); i++) {
                    System.out.println("[" + (i + 1) + "] Category: " + expenses1.get(i).getCategory() + "    | Amount: " + expenses1.get(i).getAmount() + " £    | Description: " + expenses1.get(i).getDescription());
                    count = i + 1;
                }
                System.out.println("\n");

                while (true) {
                    System.out.print("Which expense do you want to delete? (Enter a number): ");
                    String userInput = input.nextLine().trim();
                    if (userInput.isEmpty()) System.out.println("❌ Invalid input. The number cannot be empty.");
                    else {
                        try {
                            int number = Integer.parseInt(userInput);
                            if (number < 1 || number > count) {
                                System.out.println("❌ Invalid input.");
                            } else {
                                expenses1.remove(number - 1);
                                if (expenses1.isEmpty()) {
                                    expenses.remove(date);
                                }
                                System.out.println("\n✅ Expense deleted successfully!");
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Invalid input. Please enter a valid number.");
                        }
                    }
                }
            }
        }
    }

    // Save Expenses
    public File prepareFile (String path) throws IOException {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public void saveExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("\n❌ No expense found.");
        } else {
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(prepareFile("data/expenses.txt"))))) {

                double total = 0.0;
                for (Map.Entry<LocalDate, ArrayList<Expense>> entry : expenses.entrySet()) {
                    LocalDate date = entry.getKey();
                    ArrayList<Expense> expenses1 = entry.getValue();
                    pw.println("\n   Date: " + date);

                    for (Expense expense : expenses1) {
                        total += expense.getAmount();
                        pw.println(expense.toString());
                    }
                }
                pw.println("\n" + "*".repeat(50));
                pw.println("Total: " + total);
                pw.println("*".repeat(50));
                System.out.println("\nSaving data to file...");
                System.out.println("✅ Expenses saved in /data/expenses.txt");

            } catch (IOException e) {
                System.out.println("\n❌ Backup Error: " + e.getMessage());
            }
        }

    }

    // Load expenses from file
    public void loadFromFile() {
        File file = new File("data/expenses.txt");
        if (!file.exists()) {
            System.out.println("\n❌ No expense file found.");
        } else {

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                LocalDate date = null;
                String category = null;
                double amount = 0.0;
                String description = null;

                while ((line = br.readLine()) != null) {

                    if (line.trim().startsWith("Date")) {
                        try {
                            date = LocalDate.parse(line.substring(line.indexOf(":") + 1).trim());
                        } catch (DateTimeParseException e) {
                            System.out.println("❌ Error by loading: " + e.getMessage());
                        }
                    } else if (line.trim().startsWith("Category")) {
                        String[] block = line.split("\\|");
                        category = block[0].replace("Category:", "").trim();
                        amount = Double.parseDouble(block[1].replace("Amount:", " ").replace("£", "").trim());
                        description = block[2].replace("Description:", "").trim();
                    }

                    if (date != null && category != null) {
                        if (expenses.containsKey(date)) {
                            ArrayList<Expense> expenses1 = expenses.get(date);
                            expenses1.add(new Expense(category, amount, description));
                        } else {
                            ArrayList<Expense> expenses1 = new ArrayList<>();
                            expenses1.add(new Expense(category, amount, description));
                            expenses.put(date, expenses1);
                        }

                        date = null;
                        category = null;
                    }

                }
                System.out.println("\n🔄 Expenses from data/expenses has been successfully load.");

            } catch (IOException e) {
                System.out.println("❌ Error reading file: " + e.getMessage());
            }

        }
    }

}
