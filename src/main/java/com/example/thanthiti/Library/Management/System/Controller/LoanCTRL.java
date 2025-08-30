package com.example.thanthiti.Library.Management.System.Controller;

import com.example.thanthiti.Library.Management.System.DTO.LoanDTO.LoanRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.LoanDTO.LoanResponseDTO;
import com.example.thanthiti.Library.Management.System.Service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoanCTRL {

    private final LoanService loanService;

    public LoanCTRL(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/loan")
    public ResponseEntity<LoanResponseDTO> createLoan(@Valid @RequestBody LoanRequestDTO loanRequestDTO, Authentication authentication) {
        LoanResponseDTO loanResponseDTO = loanService.createLoan(loanRequestDTO,authentication);
        return ResponseEntity.status(201).body(loanResponseDTO);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("loans/return/{loanId}")
    public ResponseEntity<LoanResponseDTO> returnLoan(@PathVariable Long loanId, Authentication authentication) {
        LoanResponseDTO loanResponseDTO = loanService.returnLoan(loanId,authentication);
        return ResponseEntity.ok(loanResponseDTO);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("loans/myloan")
    public ResponseEntity<List<LoanResponseDTO>> getMyLoan(Authentication authentication) {
        List<LoanResponseDTO> loanResponseDTO = loanService.getMyLoan(authentication);
        return ResponseEntity.ok(loanResponseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin/loans/all")
    public ResponseEntity<List<LoanResponseDTO>> getAllLoan() {
        List<LoanResponseDTO> loanResponseDTO = loanService.getAllLoan();
        return ResponseEntity.ok(loanResponseDTO);
    }
    }
