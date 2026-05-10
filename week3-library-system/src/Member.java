import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member who can borrow books.
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private List<String> borrowedBooks; // stores ISBNs of borrowed books

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<String> getBorrowedBooks() { return borrowedBooks; }

    // Borrow a book (add ISBN to borrowed list)
    public void borrowBook(String isbn) {
        borrowedBooks.add(isbn);
    }

    // Return a book (remove ISBN from borrowed list)
    public boolean returnBook(String isbn) {
        return borrowedBooks.remove(isbn);
    }

    public int getBorrowedCount() {
        return borrowedBooks.size();
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-25s | %-25s | %8d |",
                memberId, name, email, borrowedBooks.size());
    }
}
