import java.util.List;
import java.util.Scanner;

/**
 * Main class - Entry point for the Library Management System.
 * Provides a menu-driven console interface.
 */
public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        library.loadSampleData();
        System.out.println("\n  Sample data loaded successfully!\n");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("  Enter your choice: ");

            switch (choice) {
                case 1:  addBook();          break;
                case 2:  removeBook();       break;
                case 3:  searchBooks();      break;
                case 4:  displayAllBooks();  break;
                case 5:  addMember();        break;
                case 6:  removeMember();     break;
                case 7:  displayAllMembers();break;
                case 8:  issueBook();        break;
                case 9:  returnBook();       break;
                case 0:
                    running = false;
                    System.out.println("\n  Thank you for using the Library Management System!");
                    System.out.println("  Goodbye!\n");
                    break;
                default:
                    System.out.println("  ✗ Invalid choice. Please try again.\n");
            }
        }
        scanner.close();
    }

    // ==================== Menu ====================

    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════════════════╗");
        System.out.println("  ║         LIBRARY MANAGEMENT SYSTEM                ║");
        System.out.println("  ║         Week 3 - Java Project                    ║");
        System.out.println("  ╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("  ┌───────────────────────────────────────┐");
        System.out.println("  │            MAIN MENU                  │");
        System.out.println("  ├───────────────────────────────────────┤");
        System.out.println("  │  1. Add Book                         │");
        System.out.println("  │  2. Remove Book                      │");
        System.out.println("  │  3. Search Books                     │");
        System.out.println("  │  4. Display All Books                │");
        System.out.println("  │  5. Register Member                  │");
        System.out.println("  │  6. Remove Member                    │");
        System.out.println("  │  7. Display All Members              │");
        System.out.println("  │  8. Issue Book                       │");
        System.out.println("  │  9. Return Book                      │");
        System.out.println("  │  0. Exit                             │");
        System.out.println("  └───────────────────────────────────────┘");
    }

    // ==================== Book Operations ====================

    private static void addBook() {
        System.out.println("\n  --- Add New Book ---");
        String isbn = readString("  Enter ISBN: ");
        String title = readString("  Enter Title: ");
        String author = readString("  Enter Author: ");
        int copies = readInt("  Enter Number of Copies: ");
        library.addBook(isbn, title, author, copies);
        System.out.println();
    }

    private static void removeBook() {
        System.out.println("\n  --- Remove Book ---");
        String isbn = readString("  Enter ISBN of book to remove: ");
        library.removeBook(isbn);
        System.out.println();
    }

    private static void searchBooks() {
        System.out.println("\n  --- Search Books ---");
        String query = readString("  Enter search query (title/author/ISBN): ");
        List<Book> results = library.searchBooks(query);
        if (results.isEmpty()) {
            System.out.println("  No books found matching: " + query);
        } else {
            System.out.println("  Found " + results.size() + " result(s):\n");
            for (Book book : results) {
                System.out.println(book);
            }
        }
        System.out.println();
    }

    private static void displayAllBooks() {
        System.out.println("\n  --- All Books ---");
        library.displayAllBooks();
        System.out.println();
    }

    // ==================== Member Operations ====================

    private static void addMember() {
        System.out.println("\n  --- Register New Member ---");
        String id = readString("  Enter Member ID: ");
        String name = readString("  Enter Name: ");
        String email = readString("  Enter Email: ");
        library.addMember(id, name, email);
        System.out.println();
    }

    private static void removeMember() {
        System.out.println("\n  --- Remove Member ---");
        String id = readString("  Enter Member ID to remove: ");
        library.removeMember(id);
        System.out.println();
    }

    private static void displayAllMembers() {
        System.out.println("\n  --- All Members ---");
        library.displayAllMembers();
        System.out.println();
    }

    // ==================== Issue & Return ====================

    private static void issueBook() {
        System.out.println("\n  --- Issue Book ---");
        String isbn = readString("  Enter Book ISBN: ");
        String memberId = readString("  Enter Member ID: ");
        library.issueBook(isbn, memberId);
        System.out.println();
    }

    private static void returnBook() {
        System.out.println("\n  --- Return Book ---");
        String isbn = readString("  Enter Book ISBN: ");
        String memberId = readString("  Enter Member ID: ");
        library.returnBook(isbn, memberId);
        System.out.println();
    }

    // ==================== Input Helpers ====================

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("  ✗ Please enter a valid number.");
            }
        }
    }
}
