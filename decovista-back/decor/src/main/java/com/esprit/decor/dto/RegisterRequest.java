package com.esprit.decor.dto;

import com.esprit.decor.entity.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private Role role;
}

