//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package main;

import java.util.*;
import service.StudentManager;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();
        List<String> subjects = new ArrayList<>();
        int choice;

        System.out.println("\n📚 Welcome to the Advanced Student Manager 🎓");
        subjectToManage(input, subjects);

        do {

            System.out.println("\n🎯 Main Menu:");
            System.out.println("    1. Add a new student");
            System.out.println("    2. Add grades to a student");
            System.out.println("    3. Calculate average for a student");
            System.out.println("    4. Find a student");
            System.out.println("    5. Remove a student");
            System.out.println("    6. Display all students");
            System.out.println("    7. Save data");
            System.out.println("    8. Load data");
            System.out.println("    9. Exit");

            while (true) {

                System.out.print("Please enter your choice: ");
                String userInput = input.nextLine().trim();
                if (userInput.isEmpty()) {
                    System.out.println("❌ Your choice cannot be empty.");
                    System.out.println("🔄 Please Try again.");
                    continue;
                }

                try {
                    choice = Integer.parseInt(userInput);

                    if (choice >= 1 && choice <= 9) {
                        break;
                    }
                    System.out.println("❌ Invalid input.");
                    System.out.println("🔄Please try again and enter a number between 1 and 9.");

                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input.");
                }

            }

            switch (choice) {

                case 1:
                    studentManager.addNewStudent(input, subjects);
                    break;

                case 2:
                    studentManager.addGrade(input);
                    break;

                case 3:
                    studentManager.average(input);
                    break;

                case 4:
                    studentManager.findStudent(input);
                    break;

                case 5:
                    studentManager.removeStudent(input);
                    break;

                case 6:
                    studentManager.displayAllStudents();
                    break;

                case 7:
                    studentManager.saveData("student.ser");
                    break;

                case 8:
                    studentManager.loadData("student.ser");
                    break;

                case 9:
                    System.out.println("\n👋 Goodbye! See you next time.");
                    break;

            }

        } while (choice != 9);

        input.close();

    }

    public static void subjectToManage(Scanner input, List<String> subjects) {

        while (true) {
            System.out.print("\n👉 Please enter the subjects you want to manage (separated by commas): ");
            String line = input.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println("❌ You must enter at least one subject.");
                continue;
            }

            String[] parts = line.split(",");
            subjects.clear();
            boolean hasError = false;

            for (String part : parts) {
                String subject = part.trim();

                if (subject.isEmpty() || !subject.matches("^[A-Za-zÀ-ÿ\\s-]+$")) {
                    System.out.println("❌ Invalid subject name: \"" + subject + "\"");
                    System.out.println("❎ Subjects must contain only letters, spaces or hyphens.");
                    hasError = true;
                    break;
                }

                subjects.add(subject);
            }

            if (!hasError) {
                System.out.println("✅ Subjects set: " + subjects);
                break;
            } else {
                System.out.println("🔁 Please try again.");
            }
        }
    }

}