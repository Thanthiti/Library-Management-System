package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.DTO.AdminDTO.AdminResponseDTO;
import com.example.thanthiti.Library.Management.System.Mapper.UserMapper;
import com.example.thanthiti.Library.Management.System.Repository.AdminRepository;
import org.springframework.stereotype.Service;

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
            .map(UserMapper::toAdminUserResponse)
            .collect(Collectors.toList());
}
}
