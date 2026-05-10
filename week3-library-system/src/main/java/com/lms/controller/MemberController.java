package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.dto.MemberRequest;
import com.lms.model.Member;
import com.lms.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Member operations.
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final LibraryService libraryService;

    public MemberController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * GET /api/members — Get all members
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllMembers() {
        List<Member> members = libraryService.getAllMembers();
        return ResponseEntity.ok(new ApiResponse(true, "Found " + members.size() + " member(s)", members));
    }

    /**
     * GET /api/members/{id} — Get a specific member by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getMember(@PathVariable String id) {
        try {
            Member member = libraryService.getMember(id);
            return ResponseEntity.ok(new ApiResponse(true, "Member found", member));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * POST /api/members — Register a new member
     * Body: { "memberId": "...", "name": "...", "email": "..." }
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addMember(@RequestBody MemberRequest request) {
        try {
            Member member = libraryService.addMember(
                    request.getMemberId(), request.getName(), request.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Member registered successfully", member));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * DELETE /api/members/{id} — Remove a member by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> removeMember(@PathVariable String id) {
        try {
            Member member = libraryService.removeMember(id);
            return ResponseEntity.ok(new ApiResponse(true, "Member removed: " + member.getName(), member));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
