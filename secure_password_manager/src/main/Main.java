package main;

import passwords.Password;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        HashMap<String, Password> passwords = new HashMap<>();
        int choice = 0;

        System.out.println("\n🔐 Welcome to the Secure Password Manager 🔐");

        do {

            // Your choice
            while (true){
                System.out.println("\nPlease choose an option:");
                System.out.println("    1. Add new password");
                System.out.println("    2. Delete password by site");
                System.out.println("    3. Search password by site");
                System.out.println("    4. Display all passwords");
                System.out.println("    5. Save passwords to file");
                System.out.println("    6. Load passwords from file");
                System.out.println("    0. Exit");


                try {
                    System.out.print("> Your choice: ");
                    String userInput = input.nextLine().trim();

                    if (userInput.isEmpty()) {
                        throw new IllegalArgumentException("Choice can not be empty.");
                    }

                    choice = Integer.parseInt(userInput);
                    if (choice >= 0 && choice <= 6) break;
                    else System.out.println("❌ Invalid input. Make sure to enter a number between 0 and 6.");

                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input.");
                }
                catch (IllegalArgumentException e) {
                    System.out.println("❌ Invalid input. " + e.getMessage());
                }

            }

            switch (choice) {

                case 0:
                    System.out.println("\n👋 Exiting... Thank for using the Password Manager!");
                    break;

                case 1:

                    System.out.println("\n🔧 Add new password");

                    while (true) {
                        System.out.print("\nEnter site name: ");
                        String siteName = input.nextLine().trim();
                        if (siteName.isEmpty()) System.out.println("❌ Invalid input.");
                        else {
                            if (findSiteName(passwords, siteName) == null) {
                                Password password = new Password();

                                while (true) {
                                    System.out.print("Enter login: ");
                                    String login = input.nextLine().trim();
                                    if (login.isEmpty()) System.out.println("❌ Invalid input.");
                                    else {
                                        password.setLogin(login);

                                        while (true) {
                                            System.out.print("Enter password (Minimum 6 Characters): ");
                                            String value = input.nextLine();
                                            if (!value.isEmpty()) {
                                                if (value.length() < 6) System.out.println("❌ Invalid input. Your password should have minimum 6 characters.");
                                                else {
                                                    password.setValue(value);
                                                    passwords.put(siteName, password);
                                                    System.out.println("\n✅ Password for " + siteName + " saved successfully!");
                                                    break;
                                                }
                                            }
                                            else System.out.println("❌ Invalid input.");
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                            System.out.println("❌ This site name already exist.");
                        }
                    }
                    break;

                case 2:

                    if (passwords.isEmpty()) {
                        System.out.println("\n❌ No password found!");
                        break;
                    }

                    System.out.println("\n🗑 Delete password by site");
                    while (true) {
                        System.out.print("\nEnter site name to delete: ");
                        String siteNameToDelete = input.nextLine().trim();
                        if (siteNameToDelete.isEmpty()) System.out.println("❌ Invalid input.");
                        else {
                            if (findSiteName(passwords, siteNameToDelete) != null) {
                                passwords.remove(siteNameToDelete);
                                System.out.println("✅ Password for " + siteNameToDelete + " deleted successfully!");
                                break;
                            } else System.out.println("❌ site name no found.");
                        }
                    }
                    break;

                case 3:

                    if (passwords.isEmpty()) {
                        System.out.println("\n❌ No password found.");
                        break;
                    }

                    System.out.println("\n🔍 Search password by site");
                    while (true) {
                        System.out.print("\nEnter site name: ");
                        String siteNameToSearch = input.nextLine().trim();
                        if (siteNameToSearch.isEmpty()) System.out.println("❌ Invalid input.");
                        else {
                            if (findSiteName(passwords, siteNameToSearch) != null) {
                                System.out.println("\n🔐 Site: " + siteNameToSearch);
                                findSiteName(passwords, siteNameToSearch).showPassword();
                                break;
                            } else System.out.println("❌ Site name no found.");
                        }
                    }
                    break;

                case 4:

                    if (passwords.isEmpty()) {
                        System.out.println("\n❌ No password found.");
                        break;
                    }

                    System.out.println("\n📋 All saved passwords:\n");

                    for (Map.Entry<String, Password> entry : passwords.entrySet()) {
                        String siteName = entry.getKey();
                        Password password = entry.getValue();
                        System.out.println("-".repeat(40));
                        System.out.println("🔐 Site: " + siteName);
                        password.showPassword();
                    }
                    break;

                case 5:
                    if (passwords.isEmpty()) {
                        System.out.println("❌ No password found.");
                        break;
                    }

                    try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(prepareFile("data/passwords.txt"))))) {

                        for (Map.Entry<String, Password> entry : passwords.entrySet()) {
                            String siteName = entry.getKey();
                            Password password = entry.getValue();

                            pw.println("\n" + "-".repeat(40));
                            pw.println("Site     : " + siteName);
                            pw.println("Login    : " + password.getLogin());
                            pw.println("Password : " + encryption(password.getValue(), 3));
                        }

                        System.out.println("\n💾 Saving passwords to file...");
                        System.out.println("✅ Passwords saved to data/passwords.txt");

                    } catch (IOException e) {
                        System.out.println("❌ Backup Error : " + e.getMessage());
                    }

                    break;

                case 6:
                    File file = new File("data/passwords.txt");
                    if (!file.exists()) {
                        System.out.println("❌ No password file found.");
                        break;
                    }

                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        String siteName = null;
                        String login = null;
                        String value = null;

                        while ((line = br.readLine()) != null) {
                            line = line.trim();

                            String trim = line.substring(line.indexOf(":") + 1).trim();
                            if (line.startsWith("Site")) {
                                siteName = trim;
                            } else if (line.startsWith("Login")) {
                                login = trim;
                            } else if (line.startsWith("Password")) {
                                value = decryption(trim, 3);
                            } else if (line.startsWith("-")) {
                                // End of a block: if all three fields are filled, create and store Password
                                if (siteName != null && login != null && value != null) {
                                    Password password = new Password();
                                    password.setLogin(login);
                                    password.setValue(value);
                                    passwords.put(siteName, password);

                                    // Reset for the next block
                                    siteName = login = value = null;
                                }
                            }
                        }

                        // Handle the last block if file does not end with dashes
                        if (siteName != null && login != null && value != null) {
                            Password password = new Password();
                            password.setLogin(login);
                            password.setValue(value);
                            passwords.put(siteName, password);
                        }

                        System.out.println("\n📂 Loading passwords from file...");
                        System.out.println("✅ Passwords successfully loaded from data/passwords.txt");

                    } catch (IOException e) {
                        System.out.println("❌ Error reading file: " + e.getMessage());
                    }
                    break;

            }

        } while (choice != 0);

        input.close();

    }

    // Universal Encryption
    public static String encryption(String value, int offset)  {

        StringBuilder result = new StringBuilder();

        for (char c : value.toCharArray()) {
            char code = (char) (c + offset);
            result.append(code);
        }
        return result.toString();

    }

    // Universal Decryption
    public static String decryption(String encryptedValue, int offset) {

        StringBuilder result = new StringBuilder();

        for (char c : encryptedValue.toCharArray()) {
            char value = (char) (c - offset);
            result.append(value);
        }
        return result.toString();

    }

    // Find a site Name
    public static Password findSiteName (HashMap<String, Password> passwords, String siteName) {
        if (passwords.containsKey(siteName)) {
            return passwords.get(siteName);
        }
        return null;
    }

    // Files manager
    public static File prepareFile(String path) throws IOException {

        File file = new File(path);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;

    }

}