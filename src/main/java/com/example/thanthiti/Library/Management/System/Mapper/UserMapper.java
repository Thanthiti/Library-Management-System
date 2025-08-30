package com.example.thanthiti.Library.Management.System.Mapper;

import com.example.thanthiti.Library.Management.System.DTO.AdminDTO.AdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserLoginResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserRegisterResponseDTO;
import com.example.thanthiti.Library.Management.System.Entity.User;

public class UserMapper
{
    // Register / Profile response
    public static UserRegisterResponseDTO toRegisterUserResponseDTO(User user) {
        UserRegisterResponseDTO dto = new UserRegisterResponseDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        return dto;
    }

    // Login response
    public static UserLoginResponseDTO toLoginResponseDTO(User user, String token) {
        UserLoginResponseDTO dto = new UserLoginResponseDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        dto.setToken(token);
        return dto;
    }

    public static AdminResponseDTO toAdminUserResponse(User user) {
        AdminResponseDTO dto = new AdminResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        dto.setCreateAt(user.getCreatedAt().toString());
        dto.setUpdateAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null);
        dto.setDeleteAt(user.getDeletedAt() != null ? user.getDeletedAt().toString() : null);
        return dto;
    }


}
