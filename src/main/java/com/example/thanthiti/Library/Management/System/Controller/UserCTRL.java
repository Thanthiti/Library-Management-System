package com.example.thanthiti.Library.Management.System.Controller;

import com.example.thanthiti.Library.Management.System.DTO.UserDTO.*;

import com.example.thanthiti.Library.Management.System.Service.AdminService;
import com.example.thanthiti.Library.Management.System.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserCTRL {
    @Autowired
    private final UserService userService;

    public UserCTRL(UserService userService, AdminService adminService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER,ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> RegisterUser(@Valid @RequestBody UserRegisterRequestDTO userRequestDTO) {
        UserRegisterResponseDTO userResponseDTO = userService.RegisterUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PreAuthorize("hasRole('USER,ADMIN')")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> LoginUser(@Valid @RequestBody UserLoginRequestDTO userRequestDTO) {
        UserLoginResponseDTO userResponseDTO= userService.LoginUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO );
      }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getUserProfile(Authentication authentication) {
        UserResponseDTO userResponseDTO = userService.getUserProfile(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<UserUpdateResDTO> updateUser(Authentication authentication, @Valid @RequestBody UserUpdateReqDTO userUpdateReqDTO) {
        UserUpdateResDTO userUpdateResDTO = userService.updateUser(authentication, userUpdateReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdateResDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        userService.deleteUser(authentication);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }


}
