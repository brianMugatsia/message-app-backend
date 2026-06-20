package com.brian.messageapp.controller;

import com.brian.messageapp.dto.JwtResponse;
import com.brian.messageapp.dto.LoginRequest;
import com.brian.messageapp.model.User;
import com.brian.messageapp.repository.UserRepository;
import com.brian.messageapp.security.JwtUtil;
import com.brian.messageapp.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:5175"})
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        String token = jwtUtil.generateToken(userDetails.getUsername(), role);

        return ResponseEntity.ok(new JwtResponse(token, role));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setRole("ROLE_USER");

        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }
}
