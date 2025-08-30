package com.example.thanthiti.Library.Management.System.Controller;

import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserLoginRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserLoginResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserRegisterRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserRegisterResponseDTO;

import com.example.thanthiti.Library.Management.System.Service.AdminService;
import com.example.thanthiti.Library.Management.System.Service.UserService;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCTRL {
    @Autowired
    private final UserService userService;

    public UserCTRL(UserService userService, AdminService adminService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> RegisterUser(@Valid @RequestBody UserRegisterRequestDTO userRequestDTO) {
        UserRegisterResponseDTO userResponseDTO = userService.RegisterUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> LoginUser(@Valid @RequestBody UserLoginRequestDTO userRequestDTO) {
        UserLoginResponseDTO userResponseDTO= userService.LoginUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO );
      }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(Authentication authentication) {
        String email = authentication.getName();
        String role = authentication.getAuthorities().toString();
        return ResponseEntity.ok("Hello " + email + " with role " + role);
    }

}
