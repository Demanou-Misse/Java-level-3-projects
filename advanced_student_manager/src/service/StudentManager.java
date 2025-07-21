package service;

import model.*;
import java.io.*;
import java.util.*;

public class StudentManager {

    private Map<String, Student> studentMap = new TreeMap<>();

    private void checkIfStudentsExist() throws StudentNotFoundException {
        if (studentMap.isEmpty()) {
            throw new StudentNotFoundException("No student found.");
        }
    }

    // Add a new Student
    public void addNewStudent(Scanner input, List<String> subjects) {

        while (true) {
            System.out.print("\nEnter student ID: ");
            String id = input.nextLine().trim().toUpperCase();
            if (id.isEmpty()) {
                System.out.println("❌ ID cannot be empty");
                continue;
            }
            if (studentMap.containsKey(id)) {
                System.out.println("❌ This ID already exist.");
                continue;
            }
            while (true) {
                System.out.print("Enter student name: ");
                String name = input.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("❌ Name cannot be empty.");
                    continue;
                }

                Map<String, Grade> gradeMap = new TreeMap<>();

                for (String subject : subjects) {
                    gradeMap.put(subject, new Grade(0.0));
                }

                studentMap.put(id, new Student(name, gradeMap));
                System.out.println("✅ Student added successfully.");
                break;
            }
            break;
        }

    }

    // Add a grade
    public void addGrade (Scanner input) {

        try {
            checkIfStudentsExist();

            while (true) {
                System.out.print("\nEnter student ID: ");
                String id = input.nextLine().trim();
                if (id.isEmpty()) {
                    System.out.println("❌ ID cannot be empty.");
                    continue;
                }
                if (!studentMap.containsKey(id)) {
                    System.out.println("❌ This ID does not exist.");
                    continue;
                }

                Student student = studentMap.get(id);
                Map<String, Grade> gradeMap = student.getGradeMap();
                List<String> subjects = new ArrayList<>();
                for (Map.Entry<String, Grade> entry : gradeMap.entrySet()) {
                    String subject = entry.getKey();
                    subjects.add(subject);
                }
                System.out.println(" \n Entering grades for: " + String.join(", ", subjects));

                int i = 0;
                do {

                    while (true) {
                        System.out.print("🔹 Enter grade for " + subjects.get(i) + " (between 1 and 100): ");
                        String userInput = input.nextLine().trim();
                        if (userInput.isEmpty()) {
                            System.out.println("❌ Grade cannot be empty.");
                            continue;
                        }
                        try {
                            double value = Double.parseDouble(userInput);

                            if (value >= 0 && value <= 100) {
                                Grade grade1 = gradeMap.get(subjects.get(i));
                                grade1.setValue(value);
                                System.out.println("    📝 Got it!");
                                i += 1;
                                break;
                            }
                            System.out.println("❌ Invalid input. Please enter a value between 0 and 100.");

                        } catch (NumberFormatException e) {
                            System.out.println("❌ Invalid input.");
                        }
                    }

                } while (i < subjects.size());

                System.out.println("✅ Grades added successfully for " + studentMap.get(id).getName() + ".");
                break;
            }

        } catch (StudentNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // Average
    public void average (Scanner input) {

        try {
            checkIfStudentsExist();

            while (true) {
                System.out.print("\n Enter student ID: ");
                String id = input.nextLine().trim();
                if (id.isEmpty()) {
                    System.out.println("❌ ID cannot be empty.");
                    continue;
                }
                if (!studentMap.containsKey(id)) {
                    System.out.println("❌ This ID does not exist.");
                    continue;
                }
                studentMap.get(id).average();
                break;
            }

        } catch (StudentNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }

    }

    // Find a student
    public void findStudent (Scanner input) {

        try {
            checkIfStudentsExist();

            while (true) {
                System.out.print("\nEnter student ID: ");
                String id = input.nextLine().trim();
                if (id.isEmpty()) {
                    System.out.println("❌ ID cannot be empty.");
                    continue;
                }
                if (!studentMap.containsKey(id)) {
                    System.out.println("❌ This ID does not exist");
                    continue;
                }

                Student student = studentMap.get(id);
                Map<String, Grade> gradeMap = student.getGradeMap();
                System.out.println("\n" + "*".repeat(50));
                System.out.println("🙍‍♂️ Student Information:");
                System.out.println("- Name: " + student.getName());
                System.out.println("- ID: " + id);
                System.out.println("- Grades:");

                double sum = 0.0;
                for (Map.Entry<String, Grade> entry : gradeMap.entrySet()) {
                    String nameGrade = entry.getKey();
                    Grade grade = entry.getValue();
                    sum += grade.getValue();
                    System.out.println("    • " + nameGrade + ": " + grade.getValue());
                }
                double average = sum / gradeMap.size();
                System.out.printf("🧾 Average: %.2f", average);
                System.out.println("\n" + "*".repeat(50));
                break;
            }

        } catch (StudentNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }

    }

    // Remove a student
    public void removeStudent (Scanner input) {

        try {
            checkIfStudentsExist();

            while (true) {
                System.out.print("\nEnter student ID: ");
                String id = input.nextLine().trim();
                if (id.isEmpty()) {
                    System.out.println("❌ ID cannot be empty.");
                    continue;
                }
                if (!studentMap.containsKey(id)) {
                    System.out.println("❌ This ID does not exist.");
                    continue;
                }

                Student student = studentMap.get(id);
                studentMap.remove(id);
                System.out.println("✅ Student \"" + student.getName() + "\" deleted successfully.");
                break;
            }

        } catch (StudentNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }

    }

    // Display all students
    public void displayAllStudents() {

        try {
            checkIfStudentsExist();

            System.out.println("\n📋 All Registered Students:");
            System.out.println("-".repeat(50));

            for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
                String id = entry.getKey();
                Student student = entry.getValue();
                Map<String, Grade> gradeMap = student.getGradeMap();
                double sum = 0.0;

                System.out.println("🙍‍♂️ID: " + id + " | Name: " + student.getName());
                System.out.println("Grades:");

                for (Map.Entry<String, Grade> entry1 : gradeMap.entrySet()) {
                    String nameGrade = entry1.getKey();
                    Grade grade = entry1.getValue();
                    sum += grade.getValue();
                    System.out.printf("• %s: %.2f | ", nameGrade, grade.getValue());
                }
                double average = sum / gradeMap.size();
                System.out.printf("\nAverage: %.2f", average);
                System.out.println("\n" + "-".repeat(50) + "\n");
            }

        } catch (StudentNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }

    }

    // Save Data
    public void saveData(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(studentMap);
            System.out.println("\n💾 Saving data to '" + filename + "'...");
            System.out.println("✅ Data saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // Load Data
    @SuppressWarnings("unchecked")
    public void loadData(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("❌ Error: The file '" + filename + "' does not exist.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            studentMap = (Map<String, Student>) in.readObject();
            System.out.println("\n🔄 Loading data from file '" + filename + "'...");
            System.out.println("✅ " + studentMap.size() + " students loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading data: " + e.getMessage());
        }
    }

}
