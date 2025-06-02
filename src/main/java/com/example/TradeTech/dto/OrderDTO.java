package com.example.TradeTech.dto;

import jakarta.validation.constraints.NotNull;

public class OrderDTO {
    @NotNull(message = "User ID is required")
    public Long userId;
}
