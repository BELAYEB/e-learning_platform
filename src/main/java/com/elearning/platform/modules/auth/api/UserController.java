package com.elearning.platform.modules.auth.api;

import com.elearning.platform.modules.auth.api.dto.ChangePasswordRequest;
import com.elearning.platform.modules.auth.api.dto.LoginRequest;
import com.elearning.platform.modules.auth.api.dto.LoginResponse;
import com.elearning.platform.modules.auth.api.dto.UserCreateRequest;
import com.elearning.platform.modules.auth.api.dto.UserResponse;
import com.elearning.platform.modules.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Management Controller
 * Handles user registration, authentication, and account management
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Register new user - Create a new user account with specified role
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreateRequest request) {
        log.info("Registering user: {}", request.email());
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * User login - Authenticate user and return JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for: {}", request.email());
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get user by ID - Retrieve user details by user ID
     * Admins can view any user profile
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        log.info("Fetching user: {}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }

    /**
     * List all users - Get list of all registered users (Admin only)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> listUsers() {
        log.info("Fetching all users");
        return ResponseEntity.ok(userService.listUsers());
    }

    /**
     * Get user by email - Retrieve user details by email address
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or #email == authentication.principal.username")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        log.info("Fetching user by email: {}", email);
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    /**
     * Update user - Update user profile information
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserCreateRequest request) {
        log.info("Updating user: {}", id);
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Change password - Change user password
     */
    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        log.info("Password change request for: {}", request.email());
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Deactivate user - Deactivate user account (Admin only)
     */
    @PutMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        log.info("Deactivating user: {}", id);
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Activate user - Activate user account (Admin only)
     */
    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        log.info("Activating user: {}", id);
        userService.activateUser(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete user - Permanently delete user account (Admin only)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
