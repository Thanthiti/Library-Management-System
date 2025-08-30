package com.example.thanthiti.Library.Management.System.DTO.UserDTO;

public class UserRegisterResponseDTO {
    private String name;
    private String email;
    private String role;

    public UserRegisterResponseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
