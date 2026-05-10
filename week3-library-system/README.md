# Library Management System — Week 3

A console-based Library Management System built in Java.

## Features

- **Book Management**: Add, remove, and search books by title/author/ISBN
- **Member Management**: Register and remove library members
- **Issue & Return**: Issue books to members and process returns
- **Borrow Limit**: Members can borrow up to 3 books at a time
- **Availability Tracking**: Track total and available copies of each book
- **Sample Data**: Pre-loaded with 5 books and 3 members for testing

## Project Structure

```
week3-library-system/
├── src/
│   ├── Book.java       # Book model (ISBN, title, author, copies)
│   ├── Member.java     # Member model (ID, name, email, borrowed books)
│   ├── Library.java    # Core service (CRUD, issue/return, search)
│   └── Main.java       # Console UI with menu-driven interface
├── out/                # Compiled .class files
└── README.md
```

## How to Run

1. **Compile**:
   ```bash
   javac -d out src/*.java
   ```
2. **Run**:
   ```bash
   java -cp out Main
   ```

## Menu Options

| Option | Description         |
|--------|---------------------|
| 1      | Add Book            |
| 2      | Remove Book         |
| 3      | Search Books        |
| 4      | Display All Books   |
| 5      | Register Member     |
| 6      | Remove Member       |
| 7      | Display All Members |
| 8      | Issue Book          |
| 9      | Return Book         |
| 0      | Exit                |
