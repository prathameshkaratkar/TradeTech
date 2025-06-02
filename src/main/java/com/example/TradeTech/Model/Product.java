package com.example.TradeTech.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;
    private String description;

    @NotNull(message = "Price is required")
    private Double price;

    @ManyToOne
    @JoinColumn(name= "category_id")
    @NotNull(message = "Category is required")
    private Category category;
}
