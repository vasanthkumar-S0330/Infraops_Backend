package com.infraops.infraops_backend.controller;

import com.infraops.infraops_backend.dto.CreateUserRequest;
import com.infraops.infraops_backend.dto.MessageResponse;
import com.infraops.infraops_backend.dto.UpdateUserRequest;
import com.infraops.infraops_backend.dto.UserResponse;
import com.infraops.infraops_backend.model.Role;
import com.infraops.infraops_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok(new MessageResponse("User created successfully"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserResponse> users = userService.getAllUsers(email, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id, Authentication authentication) {
        UserResponse user = userService.getUserById(id);

        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.name()));
        boolean isSelf = authentication.getName().equals(user.getEmail());

        if (!isAdmin && !isSelf) {
            throw new AccessDeniedException("You do not have permission to view this profile.");
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.ok(new MessageResponse("User updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }
}
