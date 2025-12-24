package com.elearning.platform.modules.auth.service;

import com.elearning.platform.common.exception.DuplicateResourceException;
import com.elearning.platform.common.exception.InvalidCredentialsException;
import com.elearning.platform.common.exception.ResourceNotFoundException;
import com.elearning.platform.modules.auth.api.dto.*;
import com.elearning.platform.modules.auth.domain.model.User;
import com.elearning.platform.modules.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        log.debug("Creating user with email: {}", request.email());

        // Check for duplicate email
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Check for duplicate username
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("Username already exists");
        }

        // Create and save user
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(User.UserRole.valueOf(request.role()))
                .active(true)
                .build();

        User saved = userRepository.save(user);
        log.info("User created successfully with ID: {}", saved.getId());

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        log.debug("Fetching user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        return toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> listUsers() {
        log.debug("Fetching all users");

        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.debug("Login attempt for email: {}", request.email());

        // Find user by email
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // Verify password
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            log.warn("Invalid password attempt for email: {}", request.email());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Check if user is active
        if (!user.getActive()) {
            log.warn("Inactive user login attempt: {}", request.email());
            throw new InvalidCredentialsException("Account is inactive");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user);

        log.info("User logged in successfully: {}", user.getEmail());

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                token
        );
    }

    /**
     * Convert User entity to UserResponse DTO
     */
    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.getActive()
        );
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        log.debug("Changing password for user: {}", request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Verify current password
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }

        // Update password
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        log.info("Password changed successfully for user: {}", request.email());
    }

    @Override
    public void deactivateUser(Long userId) {
        log.debug("Deactivating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setActive(false);
        userRepository.save(user);

        log.info("User deactivated successfully: {}", user.getEmail());
    }

    @Override
    public void activateUser(Long userId) {
        log.debug("Activating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setActive(true);
        userRepository.save(user);

        log.info("User activated successfully: {}", user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UserCreateRequest request) {
        log.debug("Updating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Check for duplicate email (if changed)
        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Check for duplicate username (if changed)
        if (!user.getUsername().equals(request.username()) && userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("Username already exists");
        }

        // Update user details
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setRole(User.UserRole.valueOf(request.role()));

        // Update password only if provided
        if (request.password() != null && !request.password().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        User updated = userRepository.save(user);
        log.info("User updated successfully: {}", updated.getEmail());

        return toResponse(updated);
    }

    @Override
    public void deleteUser(Long userId) {
        log.debug("Deleting user with ID: {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        userRepository.deleteById(userId);
        log.info("User deleted successfully with ID: {}", userId);
    }
}
