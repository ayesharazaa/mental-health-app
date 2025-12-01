package com.example.demo.controller;

import com.example.demo.auth.LoginRequest;
import com.example.demo.auth.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.NotificationService;
import com.example.demo.service.UserService;
import com.example.demo.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final NotificationService notifService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    @Autowired
    public UserController(UserService userService, NotificationService notifService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService authService) {
        this.userService = userService;
        this.notifService = notifService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    // Register user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(f -> f.getField() + ": " + f.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
                    }
        try {
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(Set.of("ROLE_USER")); // default role
            }
            User savedUser = userService.register(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        String[] roles = userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority()).toArray(String[]::new);


        return ResponseEntity.ok(new LoginResponse(token, userDetails.getUsername(), roles));
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body, Principal principal) {
        String oldPass = body.get("oldPassword");
        String newPass = body.get("newPassword");

        boolean ok = authService.changeUserPassword(principal.getName(), oldPass, newPass);

        return ok ?
                ResponseEntity.ok(Map.of("message", "Password Updated")) :
                ResponseEntity.badRequest().body(Map.of("error", "Incorrect old password"));
    }
}
