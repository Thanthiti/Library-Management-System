package com.example.thanthiti.Library.Management.System.Controller;

import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserLoginRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserLoginResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserResponseDTO;
import com.example.thanthiti.Library.Management.System.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCTRL {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> RegisterUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.RegisterUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> LoginUser(@Valid @RequestBody UserLoginRequestDTO userRequestDTO) {
        UserLoginResponseDTO userResponseDTO= userService.LoginUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO );
      }
}
