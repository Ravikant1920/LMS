package com.lms.dto;

/**
 * DTO for registering a new member via API.
 */
public class MemberRequest {
    private String memberId;
    private String name;
    private String email;

    public MemberRequest() {}

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
