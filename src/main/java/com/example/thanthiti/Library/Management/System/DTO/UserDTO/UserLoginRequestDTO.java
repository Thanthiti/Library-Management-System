package com.example.thanthiti.Library.Management.System.DTO.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginRequestDTO {
    @NotBlank(message =  "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message =  "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public UserLoginRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
