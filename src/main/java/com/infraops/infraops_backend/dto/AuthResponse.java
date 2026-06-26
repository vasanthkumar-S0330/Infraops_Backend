package com.infraops.infraops_backend.dto;

import com.infraops.infraops_backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String userId;
    private String email;
    private Role role;
}
