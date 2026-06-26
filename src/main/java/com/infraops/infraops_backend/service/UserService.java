package com.infraops.infraops_backend.service;

import com.infraops.infraops_backend.dto.CreateUserRequest;
import com.infraops.infraops_backend.dto.UpdateUserRequest;
import com.infraops.infraops_backend.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void createUser(CreateUserRequest request);
    Page<UserResponse> getAllUsers(String email, Pageable pageable);
    UserResponse getUserById(String id);
    void updateUser(String id, UpdateUserRequest request);
    void deleteUser(String id);
}
