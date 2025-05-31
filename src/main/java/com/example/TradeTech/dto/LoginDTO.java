package com.example.TradeTech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    public String email;

    @NotBlank(message = "Password is required")
    public String password;
}
