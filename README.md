# Java Level 3 Projects 🚀

Welcome! This repository contains six advanced beginner Java projects built to strengthen my understanding of **OOP**, **file management**, **collections**, and **error handling**.

Each project is organized in a dedicated folder with a clean structure (`model`, `service`, `main`, `data`) and a console-based UI. You're free to explore and expand them!

---

## 📚 Projects Overview

---

### ✅ 1. Secure Password Manager 🔐

**Goals:**

- Securely store and manage user passwords
- Use `HashMap` and text file persistence
- (Bonus) Add simple encryption

**What it does:**

- Add a new password (site, login, password)
- Delete a password by site
- Search for a password by site
- Display all saved passwords
- Automatically save/load from a `.txt` file

📌 **Concepts Used:** `HashMap`, `FileReader`, `FileWriter`, file handling, `for-each`, `Iterator`

📂 Folder: `/passwordmanager`

---

### ✅ 2. Expense Tracker 💸

**Goals:**

- Track and categorize expenses
- Organize with `HashMap<String, List<Expense>>`
- Sort by date or amount
- Save/load from file

**What it does:**

- Add an expense (date, description, amount, category)
- View all expenses sorted
- Filter by category
- Auto save/load to/from `.txt`

📌 **Concepts Used:** Nested collections, `ArrayList`, `Collections.sort()`, file I/O, simple date handling

📂 Folder: `/expensetracker`

---

### ✅ 3. Advanced Student Manager 🎓

**Goals:**

- Manage students using `HashMap<String, Student>`
- Add/remove/search students
- Serialize data using `.ser` files
- Handle custom exceptions

**What it does:**

- Create students with a unique ID
- Add grades and calculate averages
- Save/load data via serialization
- Handle missing students with custom exceptions

📌 **Concepts Used:** `Serializable`, `HashMap`, custom exceptions, `try-catch-finally`

📂 Folder: `/studentmanager`

---

### ✅ 4. File Explorer (Console) 🗂️

**Goals:**

- Navigate file system from console
- Create/delete/search files and folders
- Handle I/O exceptions

**What it does:**

- List folder contents
- Create or delete files/folders
- Search for files by name
- Robust error handling

📌 **Concepts Used:** `File`, `Scanner`, exception handling, I/O logic

📂 Folder: `/fileexplorer`

---

### ✅ 5. Mini Search Engine 🔍

**Goals:**

- Search keywords inside a `.txt` file
- Show lines where the word appears
- (Bonus) Most frequent word count

**What it does:**

- Read file line by line
- Search for keywords
- Display matches with line numbers
- Count word frequency (bonus)

📌 **Concepts Used:** `BufferedReader`, `HashMap<String, Integer>`, string handling, file parsing

📂 Folder: `/searchengine`

---

### ✅ 6. Weekly Planner Manager 📅

**Goals:**

- Manage tasks by weekday
- Use `Map<String, List<Task>>`
- Save/load tasks to/from file

**What it does:**

- Add tasks by day
- Show tasks for a specific day or the entire week
- Save and load planning from `.txt`

📌 **Concepts Used:** `Map`, `ArrayList`, file I/O, user input handling

📂 Folder: `/weeklyplanner`

---

## 🛠️ How to Run the Projects

1. Open your IDE (IntelliJ, Eclipse, VS Code…).
2. Open a project folder.
3. Run the `Main.java` file inside `/main`.
4. Use the terminal to interact with the program.

---

## 📬 Contact

If you have questions or ideas, feel free to reach out:

* GitHub: [Demanou-Misse](https://github.com/Demanou-Misse)
* Email: [missedemanou@gmail.com](mailto:missedemanou@gmail.com)

---

Happy coding! Keep growing as a developer 💻🔥
