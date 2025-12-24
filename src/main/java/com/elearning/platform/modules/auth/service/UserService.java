package com.elearning.platform.modules.auth.service;

import com.elearning.platform.modules.auth.api.dto.ChangePasswordRequest;
import com.elearning.platform.modules.auth.api.dto.LoginRequest;
import com.elearning.platform.modules.auth.api.dto.LoginResponse;
import com.elearning.platform.modules.auth.api.dto.UserCreateRequest;
import com.elearning.platform.modules.auth.api.dto.UserResponse;

import java.util.List;

/**
 * User Service Interface
 * Handles user management and authentication operations
 */
public interface UserService {

    /**
     * Register a new user
     */
    UserResponse createUser(UserCreateRequest request);

    /**
     * Get user by ID
     */
    UserResponse getUser(Long id);

    /**
     * List all users
     */
    List<UserResponse> listUsers();

    /**
     * Authenticate user and generate JWT token
     */
    LoginResponse login(LoginRequest request);

    /**
     * Change user password
     */
    void changePassword(ChangePasswordRequest request);

    /**
     * Deactivate user account
     */
    void deactivateUser(Long userId);

    /**
     * Activate user account
     */
    void activateUser(Long userId);

    /**
     * Get user by email
     */
    UserResponse getUserByEmail(String email);

    /**
     * Update user profile
     */
    UserResponse updateUser(Long userId, UserCreateRequest request);

    /**
     * Delete user account
     */
    void deleteUser(Long userId);
}


