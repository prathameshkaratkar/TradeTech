package com.example.TradeTech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be 3 and 50 characters")
    public String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    public String password;
}
