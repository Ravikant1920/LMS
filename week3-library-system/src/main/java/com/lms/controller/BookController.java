package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.dto.BookRequest;
import com.lms.dto.IssueReturnRequest;
import com.lms.model.Book;
import com.lms.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Book operations.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * GET /api/books — Get all books
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        return ResponseEntity.ok(new ApiResponse(true, "Found " + books.size() + " book(s)", books));
    }

    /**
     * GET /api/books/{isbn} — Get a specific book by ISBN
     */
    @GetMapping("/{isbn}")
    public ResponseEntity<ApiResponse> getBook(@PathVariable String isbn) {
        try {
            Book book = libraryService.getBook(isbn);
            return ResponseEntity.ok(new ApiResponse(true, "Book found", book));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * POST /api/books — Add a new book
     * Body: { "isbn": "...", "title": "...", "author": "...", "totalCopies": 3 }
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addBook(@RequestBody BookRequest request) {
        try {
            Book book = libraryService.addBook(
                    request.getIsbn(), request.getTitle(),
                    request.getAuthor(), request.getTotalCopies());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Book added successfully", book));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * DELETE /api/books/{isbn} — Remove a book by ISBN
     */
    @DeleteMapping("/{isbn}")
    public ResponseEntity<ApiResponse> removeBook(@PathVariable String isbn) {
        try {
            Book book = libraryService.removeBook(isbn);
            return ResponseEntity.ok(new ApiResponse(true, "Book removed: " + book.getTitle(), book));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * GET /api/books/search?q=java — Search books by title, author, or ISBN
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchBooks(@RequestParam String q) {
        List<Book> results = libraryService.searchBooks(q);
        return ResponseEntity.ok(new ApiResponse(true, "Found " + results.size() + " result(s)", results));
    }

    /**
     * POST /api/books/issue — Issue a book to a member
     * Body: { "isbn": "...", "memberId": "..." }
     */
    @PostMapping("/issue")
    public ResponseEntity<ApiResponse> issueBook(@RequestBody IssueReturnRequest request) {
        try {
            Map<String, Object> result = libraryService.issueBook(request.getIsbn(), request.getMemberId());
            return ResponseEntity.ok(new ApiResponse(true, "Book issued successfully", result));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * POST /api/books/return — Return a book from a member
     * Body: { "isbn": "...", "memberId": "..." }
     */
    @PostMapping("/return")
    public ResponseEntity<ApiResponse> returnBook(@RequestBody IssueReturnRequest request) {
        try {
            Map<String, Object> result = libraryService.returnBook(request.getIsbn(), request.getMemberId());
            return ResponseEntity.ok(new ApiResponse(true, "Book returned successfully", result));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
