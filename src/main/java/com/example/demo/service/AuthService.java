package com.example.demo.service;

import com.example.demo.email.EmailService;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder,
                       JwtUtil jwtUtil, EmailService emailService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    // Register a new user
    public String register(User user, String adminCode) throws Exception {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
        }

        // Encode password
        user.setPassword(encoder.encode(user.getPassword()));

        // Assign roles
        Set<String> roles = new HashSet<>();

        // Check for admin code
        if ("admin-secret".equals(adminCode)) {

            roles.add("ROLE_ADMIN");
        }
        else
        {
            roles.add("ROLE_USER");
        }

        user.setRoles(roles);

        userRepository.save(user);
        return "Registered successfully";
    }

    // Login and return JWT token
    public String login(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(username, username);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }

        User user = optionalUser.get();
        if (!encoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        // Generate JWT token
        return jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority(r)).toList()));
    }
    public String generateResetToken(User user) {
        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30));

        userRepository.save(user);
        return token;
    }
    public boolean resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElse(null);

        if (user == null) return false;
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) return false;

        user.setPassword(encoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);

        return true;
    }
    public void sendResetEmail(String to, String link) {
        String subject = "Password Reset Request";
        String body = "<p>You requested to reset your password.</p>"
                + "<p>Click the link below to reset your password:</p>"
                + "<a href=\"" + link + "\">Reset Password</a>"
                + "<p>This link will expire in 30 minutes.</p>"
                + "<p>If you didn't request this, please ignore this email.</p>";

        emailService.sendEmail(to, subject, body);
    }

    @Transactional
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        userRepository.findAll().forEach(user -> {
            if (user.getResetToken() != null && 
                user.getResetTokenExpiry() != null && 
                user.getResetTokenExpiry().isBefore(now)) {
                user.setResetToken(null);
                user.setResetTokenExpiry(null);
                userRepository.save(user);
            }
        });
    }

    @Transactional
    public boolean changeUserPassword(String usernameOrEmail, String oldPassword, String newPassword) {
        // find by username or email (your repository already has this helper)
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        // verify old password
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        // set new encoded password
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }


}
