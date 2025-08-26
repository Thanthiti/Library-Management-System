package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.DTO.AdminDTO.AdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserResponseDTO;
import com.example.thanthiti.Library.Management.System.Entity.User;
import com.example.thanthiti.Library.Management.System.Repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

//  Create  Service for admin operations can be added here and Get all users (admin only)
public List<AdminResponseDTO> getAllUsers() {
    return adminRepository.findAll().stream()
            .map(user -> {
                AdminResponseDTO AdminResponseDto = new AdminResponseDTO();
                AdminResponseDto.setId(user.getId());
                AdminResponseDto.setName(user.getName());
                AdminResponseDto.setEmail(user.getEmail());
                AdminResponseDto.setRole(user.getRole().toString());
                AdminResponseDto.setCreateAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
                AdminResponseDto.setUpdateAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null);
                AdminResponseDto.setDeleteAt(user.getDeletedAt() != null ? user.getDeletedAt().toString() : null);
                return AdminResponseDto;
            })
            .collect(Collectors.toList());
}

}
