package com.lms.dto;

/**
 * DTO for issuing or returning a book.
 */
public class IssueReturnRequest {
    private String isbn;
    private String memberId;

    public IssueReturnRequest() {}

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
}
