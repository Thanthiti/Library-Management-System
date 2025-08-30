package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.Config.JwtUtil;

import com.example.thanthiti.Library.Management.System.DTO.UserDTO.*;

import com.example.thanthiti.Library.Management.System.Entity.User;
import com.example.thanthiti.Library.Management.System.Mapper.UserMapper;
import com.example.thanthiti.Library.Management.System.Repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil ;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder =  passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
//    Service to handle user registration
    public UserRegisterResponseDTO RegisterUser(UserRegisterRequestDTO userRequestDTO) {
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

        return UserMapper.toRegisterUserResponseDTO(user);
    }

    public UserLoginResponseDTO LoginUser(UserLoginRequestDTO loginRequestDTO) {
        // Logic to authenticate a user and generate a token
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Generate a simple token (in a real application, use JWT or similar)
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

        return UserMapper.toLoginResponseDTO(user, token);
    }

    public UserResponseDTO getUserProfile(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toUserResponseDTO(user);
    }

    public UserUpdateResDTO updateUser(Authentication authentication,UserUpdateReqDTO requestDTO) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update Name
        if(requestDTO.getName() != null && !requestDTO.getName().isBlank()) {
            user.setName(requestDTO.getName());
        }

        // Update Email
        if(requestDTO.getEmail() != null && !requestDTO.getEmail().isBlank()) {
            if(userRepository.findByEmail(requestDTO.getEmail())
                    .filter(u -> !u.getId().equals(user.getId()))
                    .isPresent()) {
                throw new RuntimeException("Email already in use");
            }
            user.setEmail(requestDTO.getEmail());
        }

        // Update Password
        if(requestDTO.getOldPassword() != null && requestDTO.getNewPassword() != null) {
            if(!passwordEncoder.matches(requestDTO.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("Old password incorrect");
            }
            user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.toUserUpdateResDTO(updatedUser,"User updated successfully");
    }

    public void deleteUser(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
