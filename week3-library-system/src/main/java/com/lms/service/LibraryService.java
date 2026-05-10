package com.lms.service;

import com.lms.model.Book;
import com.lms.model.Member;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Core library service that manages books and members.
 */
@Service
public class LibraryService {

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private static final int MAX_BORROW_LIMIT = 3;

    /**
     * Load sample data on startup.
     */
    @PostConstruct
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

    // ==================== Book Operations ====================

    public Book addBook(String isbn, String title, String author, int copies) {
        if (books.containsKey(isbn)) {
            throw new RuntimeException("A book with ISBN " + isbn + " already exists.");
        }
        Book book = new Book(isbn, title, author, copies);
        books.put(isbn, book);
        return book;
    }

    public Book getBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new RuntimeException("No book found with ISBN: " + isbn);
        }
        return book;
    }

    public Book removeBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new RuntimeException("No book found with ISBN: " + isbn);
        }
        if (book.getAvailableCopies() != book.getTotalCopies()) {
            throw new RuntimeException("Cannot remove '" + book.getTitle() + "' — some copies are still issued.");
        }
        books.remove(isbn);
        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Book> searchBooks(String query) {
        String lowerQuery = query.toLowerCase();
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lowerQuery) ||
                             b.getAuthor().toLowerCase().contains(lowerQuery) ||
                             b.getIsbn().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    // ==================== Member Operations ====================

    public Member addMember(String memberId, String name, String email) {
        if (members.containsKey(memberId)) {
            throw new RuntimeException("A member with ID " + memberId + " already exists.");
        }
        Member member = new Member(memberId, name, email);
        members.put(memberId, member);
        return member;
    }

    public Member getMember(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            throw new RuntimeException("No member found with ID: " + memberId);
        }
        return member;
    }

    public Member removeMember(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            throw new RuntimeException("No member found with ID: " + memberId);
        }
        if (member.getBorrowedCount() > 0) {
            throw new RuntimeException("Cannot remove '" + member.getName() +
                    "' — they have " + member.getBorrowedCount() + " book(s) still borrowed.");
        }
        members.remove(memberId);
        return member;
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    // ==================== Issue & Return ====================

    public Map<String, Object> issueBook(String isbn, String memberId) {
        Book book = getBook(isbn);
        Member member = getMember(memberId);

        if (member.getBorrowedCount() >= MAX_BORROW_LIMIT) {
            throw new RuntimeException(member.getName() + " has reached the maximum borrow limit (" +
                    MAX_BORROW_LIMIT + " books).");
        }
        if (!book.isAvailable()) {
            throw new RuntimeException("'" + book.getTitle() + "' is not available (all copies are issued).");
        }
        if (member.getBorrowedBooks().contains(isbn)) {
            throw new RuntimeException(member.getName() + " already has a copy of '" + book.getTitle() + "'.");
        }

        book.issueCopy();
        member.borrowBook(isbn);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("book", book);
        result.put("member", member);
        return result;
    }

    public Map<String, Object> returnBook(String isbn, String memberId) {
        Book book = getBook(isbn);
        Member member = getMember(memberId);

        if (!member.returnBook(isbn)) {
            throw new RuntimeException(member.getName() + " does not have '" + book.getTitle() + "' borrowed.");
        }

        book.returnCopy();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("book", book);
        result.put("member", member);
        return result;
    }
}
