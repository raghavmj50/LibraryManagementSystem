package com.avaali.library.controller;

import com.avaali.library.dto.request.MemberRequest;
import com.avaali.library.dto.response.MemberResponse;
import com.avaali.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(
            @Valid @RequestBody MemberRequest request) {

        MemberResponse response =
                memberService.createMember(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(
            @PathVariable Integer id) {

        MemberResponse response =
                memberService.getMemberById(id);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable Integer id,
            @Valid @RequestBody MemberRequest request) {

        MemberResponse response =
                memberService.updateMember(id, request);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<Page<MemberResponse>> getMembers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            Pageable pageable) {

        Page<MemberResponse> response =
                memberService.getMembers(
                        name,
                        email,
                        pageable
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Integer id) {

        memberService.deleteMember(id);

        return ResponseEntity.noContent().build();
    }
}
