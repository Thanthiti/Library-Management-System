package com.example.thanthiti.Library.Management.System.Controller;

import com.example.thanthiti.Library.Management.System.DTO.AdminDTO.AdminResponseDTO;
import com.example.thanthiti.Library.Management.System.Service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class AdminCTRL {

    private final AdminService adminService;

    public AdminCTRL(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<AdminResponseDTO>> getAllUsers() {
        List<AdminResponseDTO> adminResponseDTO = adminService.getAllUsers();
        return ResponseEntity.ok(adminResponseDTO);
    }


}
