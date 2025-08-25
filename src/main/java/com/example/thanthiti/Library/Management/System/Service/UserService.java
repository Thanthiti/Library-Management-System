package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.Config.JwtUtil;

import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserLoginRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserLoginResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.UserDTO.UserResponseDTO;

import com.example.thanthiti.Library.Management.System.Entity.User;
import com.example.thanthiti.Library.Management.System.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil ;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder =  passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
//    Service to handle user registration
    public UserResponseDTO RegisterUser(UserRequestDTO userRequestDTO) {
        // Logic to register a new user
        User user = new User();
        user.setName(userRequestDTO.getName());

        if(userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setEmail(userRequestDTO.getEmail());

        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        if (userRequestDTO.getRole() == null || userRequestDTO.getRole().isBlank()) {
            user.setRole(User.Role.USER);
        } else {
            user.setRole(User.Role.valueOf(userRequestDTO.getRole().toUpperCase()));
        }

        user.setCreatedAt(java.time.LocalDateTime.now());
        user = userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole().toString());
        userResponseDTO.setCreateAt(user.getCreatedAt().toString());
        userResponseDTO.setUpdateAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null);
        userResponseDTO.setDeleteAt(user.getDeletedAt() != null ? user.getDeletedAt().toString() : null);
        return userResponseDTO;
    }

    public UserLoginResponseDTO LoginUser(UserLoginRequestDTO loginRequestDTO) {
        // Logic to authenticate a user and generate a token
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Generate a simple token (in a real application, use JWT or similar)
        String token = jwtUtil.generateToken(user.getEmail());

        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole().toString());
        responseDTO.setToken(token);

        return responseDTO;
    }
}
