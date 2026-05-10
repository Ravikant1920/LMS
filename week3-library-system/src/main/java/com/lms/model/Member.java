package com.lms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member who can borrow books.
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private List<String> borrowedBooks;

    public Member() {
        this.borrowedBooks = new ArrayList<>();
    }

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and Setters
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getBorrowedBooks() { return borrowedBooks; }
    public void setBorrowedBooks(List<String> borrowedBooks) { this.borrowedBooks = borrowedBooks; }

    // Borrow a book
    public void borrowBook(String isbn) {
        borrowedBooks.add(isbn);
    }

    // Return a book
    public boolean returnBook(String isbn) {
        return borrowedBooks.remove(isbn);
    }

    public int getBorrowedCount() {
        return borrowedBooks.size();
    }
}
