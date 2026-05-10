import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Core library service that manages books and members.
 * Handles all operations: add/remove books, register members, issue/return books, search.
 */
public class Library {
    private Map<String, Book> books;       // ISBN -> Book
    private Map<String, Member> members;   // MemberId -> Member
    private static final int MAX_BORROW_LIMIT = 3;

    public Library() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
    }

    // ==================== Book Operations ====================

    /**
     * Add a new book to the library.
     */
    public boolean addBook(String isbn, String title, String author, int copies) {
        if (books.containsKey(isbn)) {
            System.out.println("  ✗ A book with ISBN " + isbn + " already exists.");
            return false;
        }
        books.put(isbn, new Book(isbn, title, author, copies));
        System.out.println("  ✓ Book added successfully: " + title);
        return true;
    }

    /**
     * Remove a book from the library by ISBN.
     */
    public boolean removeBook(String isbn) {
        if (!books.containsKey(isbn)) {
            System.out.println("  ✗ No book found with ISBN: " + isbn);
            return false;
        }
        Book book = books.get(isbn);
        if (book.getAvailableCopies() != book.getTotalCopies()) {
            System.out.println("  ✗ Cannot remove '" + book.getTitle() + "' — some copies are still issued.");
            return false;
        }
        books.remove(isbn);
        System.out.println("  ✓ Book removed: " + book.getTitle());
        return true;
    }

    /**
     * Search books by title or author (case-insensitive partial match).
     */
    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(lowerQuery) ||
                book.getAuthor().toLowerCase().contains(lowerQuery) ||
                book.getIsbn().toLowerCase().contains(lowerQuery)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Display all books in a formatted table.
     */
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("  No books in the library.");
            return;
        }
        printBookTableHeader();
        for (Book book : books.values()) {
            System.out.println(book);
        }
        printTableFooter(107);
        System.out.println("  Total books: " + books.size());
    }

    // ==================== Member Operations ====================

    /**
     * Register a new member.
     */
    public boolean addMember(String memberId, String name, String email) {
        if (members.containsKey(memberId)) {
            System.out.println("  ✗ A member with ID " + memberId + " already exists.");
            return false;
        }
        members.put(memberId, new Member(memberId, name, email));
        System.out.println("  ✓ Member registered: " + name + " (ID: " + memberId + ")");
        return true;
    }

    /**
     * Remove a member from the library.
     */
    public boolean removeMember(String memberId) {
        if (!members.containsKey(memberId)) {
            System.out.println("  ✗ No member found with ID: " + memberId);
            return false;
        }
        Member member = members.get(memberId);
        if (member.getBorrowedCount() > 0) {
            System.out.println("  ✗ Cannot remove '" + member.getName() + "' — they have " +
                    member.getBorrowedCount() + " book(s) still borrowed.");
            return false;
        }
        members.remove(memberId);
        System.out.println("  ✓ Member removed: " + member.getName());
        return true;
    }

    /**
     * Display all members in a formatted table.
     */
    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("  No members registered.");
            return;
        }
        printMemberTableHeader();
        for (Member member : members.values()) {
            System.out.println(member);
        }
        printTableFooter(82);
        System.out.println("  Total members: " + members.size());
    }

    // ==================== Issue & Return ====================

    /**
     * Issue a book to a member.
     */
    public boolean issueBook(String isbn, String memberId) {
        // Validate book
        if (!books.containsKey(isbn)) {
            System.out.println("  ✗ No book found with ISBN: " + isbn);
            return false;
        }
        // Validate member
        if (!members.containsKey(memberId)) {
            System.out.println("  ✗ No member found with ID: " + memberId);
            return false;
        }

        Book book = books.get(isbn);
        Member member = members.get(memberId);

        // Check borrow limit
        if (member.getBorrowedCount() >= MAX_BORROW_LIMIT) {
            System.out.println("  ✗ " + member.getName() + " has reached the maximum borrow limit (" +
                    MAX_BORROW_LIMIT + " books).");
            return false;
        }

        // Check availability
        if (!book.isAvailable()) {
            System.out.println("  ✗ '" + book.getTitle() + "' is not available (all copies are issued).");
            return false;
        }

        // Check if member already has this book
        if (member.getBorrowedBooks().contains(isbn)) {
            System.out.println("  ✗ " + member.getName() + " already has a copy of '" + book.getTitle() + "'.");
            return false;
        }

        // Issue the book
        book.issueCopy();
        member.borrowBook(isbn);
        System.out.println("  ✓ '" + book.getTitle() + "' issued to " + member.getName());
        System.out.println("    Remaining copies: " + book.getAvailableCopies() + "/" + book.getTotalCopies());
        return true;
    }

    /**
     * Return a book from a member.
     */
    public boolean returnBook(String isbn, String memberId) {
        // Validate member
        if (!members.containsKey(memberId)) {
            System.out.println("  ✗ No member found with ID: " + memberId);
            return false;
        }
        // Validate book
        if (!books.containsKey(isbn)) {
            System.out.println("  ✗ No book found with ISBN: " + isbn);
            return false;
        }

        Book book = books.get(isbn);
        Member member = members.get(memberId);

        // Check if the member actually has this book
        if (!member.returnBook(isbn)) {
            System.out.println("  ✗ " + member.getName() + " does not have '" + book.getTitle() + "' borrowed.");
            return false;
        }

        book.returnCopy();
        System.out.println("  ✓ '" + book.getTitle() + "' returned by " + member.getName());
        System.out.println("    Available copies: " + book.getAvailableCopies() + "/" + book.getTotalCopies());
        return true;
    }

    // ==================== Helper Methods ====================

    /**
     * Load sample data for testing.
     */
    public void loadSampleData() {
        addBook("978-0-13-468599-1", "The Pragmatic Programmer", "David Thomas", 3);
        addBook("978-0-596-00712-6", "Head First Design Patterns", "Eric Freeman", 2);
        addBook("978-0-13-235088-4", "Clean Code", "Robert C. Martin", 4);
        addBook("978-0-201-63361-0", "Design Patterns", "Gang of Four", 2);
        addBook("978-0-13-468747-6", "Effective Java", "Joshua Bloch", 3);

        addMember("M001", "Rahul Sharma", "rahul@email.com");
        addMember("M002", "Priya Patel", "priya@email.com");
        addMember("M003", "Amit Kumar", "amit@email.com");
    }

    private void printBookTableHeader() {
        System.out.println("  +" + "-".repeat(15) + "+" + "-".repeat(32) + "+" +
                "-".repeat(22) + "+" + "-".repeat(8) + "+" + "-".repeat(11) + "+");
        System.out.printf("  | %-13s | %-30s | %-20s | %6s | %9s |%n",
                "ISBN", "Title", "Author", "Total", "Available");
        System.out.println("  +" + "-".repeat(15) + "+" + "-".repeat(32) + "+" +
                "-".repeat(22) + "+" + "-".repeat(8) + "+" + "-".repeat(11) + "+");
    }

    private void printMemberTableHeader() {
        System.out.println("  +" + "-".repeat(12) + "+" + "-".repeat(27) + "+" +
                "-".repeat(27) + "+" + "-".repeat(10) + "+");
        System.out.printf("  | %-10s | %-25s | %-25s | %8s |%n",
                "Member ID", "Name", "Email", "Borrowed");
        System.out.println("  +" + "-".repeat(12) + "+" + "-".repeat(27) + "+" +
                "-".repeat(27) + "+" + "-".repeat(10) + "+");
    }

    private void printTableFooter(int width) {
        System.out.println("  +" + "-".repeat(width - 4) + "+");
    }
}
