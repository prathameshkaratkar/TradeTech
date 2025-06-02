package com.example.TradeTech.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    @NotBlank(message = "Category name is required")
    public String name;



}
