package service;

import model.Task;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class PlanningManager {

    private Map<String, List<Task>> map = new HashMap<>();
    List<String> days = Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday");

    // Valid Day
    public boolean validDay (String day) {
        return !days.contains(day.toLowerCase());
    }

    // Valid Time
    public LocalTime validTime(Scanner input) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.HOUR_OF_DAY)
                .appendLiteral(':')
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .toFormatter();

        while (true) {
            System.out.print("Enter the task time (e.g., 10:30): ");
            String userInput = input.nextLine().trim();
            if (userInput.isEmpty()) {
                System.out.println("❌ Time cannot be empty.");
                continue;
            }
            try {
                return LocalTime.parse(userInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid format. Please enter a valid format (e.g., 10:30).");
            }
        }
    }

    // Add a Task
    public void addTask (Scanner input) {

        String day;
        while (true) {
            System.out.print("\nEnter the day (e.g., Monday): ");
            day = input.nextLine().trim();
            if (day.isEmpty()) {
                System.out.println("❌ Day cannot be empty.");
                continue;
            }
            if (validDay(day)) {
                System.out.println("❌ Invalid input. Enter a valid day (e.g., Monday).");
                continue;
            }
            break;
        }

        String title;
        while (true) {
            System.out.print("Enter the task title: ");
            title = input.nextLine().trim();
            if (!title.isEmpty()) break;
            System.out.println("❌ Title cannot be empty.");
        }

        LocalTime time = validTime(input);

         if (!map.containsKey(day)) {
             List<Task> taskList = new ArrayList<>();
             taskList.add(new Task(title, time));
             map.put(day.toLowerCase(), taskList);
         } else {
             List<Task> taskList = map.get(day);
             taskList.add(new Task(title, time));
         }
         System.out.println("\n✅Task added successfully!");
    }

    // Show Tasks for a Specific Day
    public void showTasksDay (Scanner input) {
        if (map.isEmpty()) {
            System.out.println("\n❌ No Task found.");
            return;
        }

        String day;
        while (true) {
            System.out.print("Enter the day (e.g., Monday): ");
            day = input.nextLine().trim().toLowerCase();
            if (day.isEmpty()) {
                System.out.println("❌ Day cannot be empty.");
                continue;
            }
            if (validDay(day)) {
                System.out.println("❌ Invalid input. Enter a valid day (e.g., Monday).");
                continue;
            }
            if (!map.containsKey(day)) {
                System.out.println("\n⛔ No Task found for this day.");
                return;
            }

            System.out.println("\n🧾 Tasks for " + day + ":");
            List<Task> taskList = map.get(day);
            for (Task task : taskList) {
                System.out.println("    - [" + task.getTime() + "] " + task.getTitle());
            }
            return;
        }
    }

    //Show Weekly Schedule
    public void showWeeklySchedule () {
        if (map.isEmpty()) {
            System.out.println("\n❌ No Task found.");
            return;
        }

        System.out.println("\n📅 Weekly Schedule:\n");
        for (String day : days) {
            if (!map.containsKey(day)) {
                System.out.println(day + ": (No tasks)");
            } else {
                List<Task> taskList = map.get(day);
                System.out.println(day + ":");
                for (Task task : taskList) {
                    System.out.println(task.toString());
                }
            }
        }
    }

    // Prepare File
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

    // Save to file
    public void saveToFile () {
        if (map.isEmpty()) {
            System.out.println("\n❌ No Task found to save.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(prepareFile("data/planning.txt"))))) {

            pw.println("Weekly Schedule:\n");
            for (String day : days) {
                if (!map.containsKey(day)) {
                    pw.println(day + ": (No tasks)");
                } else {
                    List<Task> taskList = map.get(day);
                    pw.println(day + ":");
                    for (Task task : taskList) {
                        pw.println(task.toString());
                    }
                }
            }

            System.out.println("\n💾 Saving tasks to \"data/planning.txt\"...");
            System.out.println("✅ Tasks saved successfully!");

        } catch (IOException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // Load Data from File
    public void loadData () {
        File file = new File("data/planning.txt");
        if (!file.exists()) {
            System.out.println("\n❌ No Task file found");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String day = null;
            LocalTime time;
            String title;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                for (String d : days) {
                    if (line.startsWith(d)) {
                        line = line.substring(line.indexOf(":") + 1).trim();
                        if (!line.equals("(No tasks)")) day = d;
                        break;
                    } else if (line.startsWith("-") && day != null) {
                        title = line.substring(line.indexOf("]") + 2);
                        time = LocalTime.parse((line.substring((line.indexOf("[") + 1), line.indexOf("]"))));
                        map.computeIfAbsent(day, k -> new ArrayList<>()).add(new Task(title, time));
                        break;
                    }
                }
            }
            System.out.println("\n📂 Loading data from \"data/planning.txt\"...");
            System.out.println("✅ Data loaded successfully!");

        } catch (IOException e) {
            System.out.println("❌ " + e.getMessage());
        }

    }

}
