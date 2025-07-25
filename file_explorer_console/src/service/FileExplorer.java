
package service;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileExplorer {

    // List directory contents
    public void listDirectoryContents (Scanner input) {

        while (true) {
            System.out.print("\n🔍 Enter the path of the directory: ");
            String path = input.nextLine().trim();
            if (path.isEmpty()) {
                System.out.println("❌ The path cannot be empty.");
                continue;
            }

            File directory = new File(path);
            if (directory.exists() && directory.isDirectory()) {
                File[] contents = directory.listFiles();

                if (contents != null && contents.length > 0) {
                    System.out.println("\nContents of directory: ");

                    for (File content : contents) {
                        if (content.isFile()) {
                            System.out.println("    - 📄 " + content.getName());
                        } else if (content.isDirectory()) {
                            System.out.println("    - 📁 " + content.getName());
                        }
                    }
                    System.out.println("\n" + "-".repeat(50));

                } else {
                    System.out.println("❌ Cannot access folder or folder does not exist.");
                }
            } else {
                System.out.println("❌ The specified path is not a valid directory.");
            }
            return;
        }
    }

    // create a file or folder
    public void createFileOrFolder (Scanner input) throws IOException {

        int userChoice;
        while (true) {
            System.out.print("\n📁 Do you want to create a File (1) or a Folder (2)? Make a choice: ");
            String userInput = input.nextLine().trim();
            if (userInput.isEmpty()) {
                System.out.println("❌ Your choice cannot be empty.");
                continue;
            }

            try {
                userChoice = Integer.parseInt(userInput);

                if (userChoice == 1 || userChoice ==2) break;
                System.out.println("❌ Invalid input. Enter 1 or 2.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input.");
            }
        }

        while (true) {
            System.out.print("👉 Enter the full path for the new " + ((userChoice == 1) ? "file: " : "folder: "));
            String path = input.nextLine().trim();
            if (path.isEmpty()) {
                System.out.println("❌ The path cannot be empty.");
                continue;
            }

            File target = new File(path);
            File parent = target.getParentFile();

            boolean success;
            if (parent == null || !parent.exists()) {
                System.out.println("❌ The specified path is not a valid directory.");
            } else {
                if (target.exists()) {
                    System.out.println("❌ This " + (target.isFile() ? "file " : "folder ") + "already exist.");
                    continue;
                }

                if (userChoice == 1) {
                    success = target.createNewFile();
                    if (success) {
                        System.out.println("✅ File created successfully!");
                        System.out.println("\n" + "-".repeat(50));
                        return;
                    } else System.out.println("❌ Something went wrong by creating the File.");

                } else {
                    success = target.mkdirs();
                    if (success) {
                        System.out.println("✅ Folder created successfully!");
                        System.out.println("\n" + "-".repeat(50));
                        return;
                    } else System.out.println("❌ Something went wrong by creating the Folder.");
                }
            }
        }
    }

    // Delete a file or folder
    public void deleteFileOrFolder (Scanner input) throws IOException {

        while (true) {
            System.out.print("\n👉 Enter the full path of the file/folder to delete: ");
            String path = input.nextLine().trim();
            if (path.isEmpty()) {
                System.out.println("❌ Path cannot be empty.");
                continue;
            }

            File target = new File(path);
            File parent = target.getParentFile();


            if (parent == null || !parent.exists()) {
                System.out.println("❌ The specified path is not a valid directory.");
            } else {
                if (!target.exists()) {
                    System.out.println("❌ This " + (target.isFile() ? "file " : "folder ") + "do not exist.");
                    continue;
                }
                boolean success;
                while (true) {
                    System.out.print("💀 Are you sure you want to delete \"" + target.getName() + "\"? (yes/no): ");
                    String decision = input.nextLine().trim().toLowerCase();
                    switch (decision) {
                        case "":
                            System.out.println("❌ Decision cannot be empty.");
                            continue;
                        case "yes":
                            success = deleteRecursively(target);
                            if (success) {
                                System.out.println("✅ Delete successfully!");
                                System.out.println("\n" + "-".repeat(50));
                            } else System.out.println("❌ Something went wrong by deleting.");
                            return;
                        case "no":
                            System.out.println("📝 Got it! Your " + (target.isFile() ? "file " : "folder ") + "won't be deleted.");
                            return;
                        default:
                            System.out.println("❌ Invalid input. Enter yes or no.");
                    }
                }
            }
        }
    }

    private boolean deleteRecursively(File file) {
        if (!file.exists()) return false;

        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteRecursively(f);
                }
            }
        }

        return file.delete();
    }


    // Search a file by name
    public void searchFile (Scanner input) throws IOException{

        while (true) {
            System.out.print("\n🔍 Enter the path of the directory to search in: ");
            String path = input.nextLine().trim();
            if (path.isEmpty()) {
                System.out.println("❌ Path cannot be empty.");
                continue;
            }

            File target = new File(path);
            File parent = target.getParentFile();

            if (parent == null || !parent.exists()) {
                System.out.println("❌ The specified path is not a valid directory.");
                continue;
            }
            if (!target.isDirectory()) {
                System.out.println("❌ The specified path is not a directory.");
                System.out.println("🔄 Try again. Make sure to enter the path of a directory.");
                continue;
            }

            while (true) {
                System.out.print("🔎 Enter the file name to search: ");
                String nameFile = input.nextLine().trim();
                if (nameFile.isEmpty()) {
                    System.out.println("❌ The file name cannot be empty.");
                    continue;
                }

                System.out.println("Searching...");
                boolean found = findFile(target, nameFile);
                if (!found) {
                    System.out.println("\n❌ sorry: File \"" + nameFile + "\" not found");
                    System.out.println("\n" + "-".repeat(50));
                }
                break;
            }
            break;
        }


    }

    private boolean findFile (File directory, String nameFile) {
        File[] contents = directory.listFiles();

        boolean success = false;
        for (File content : contents) {
            if (content.isFile()) {
                if (nameFile.equalsIgnoreCase(content.getName())) {
                    System.out.println("\n📄 Found: " + content.getPath());
                    System.out.println("\n" + "-".repeat(50));
                    success = true;
                }
            } else if (content.isDirectory()) {
                success |= findFile(content, nameFile);
            }
        }

        return success;
    }

}
