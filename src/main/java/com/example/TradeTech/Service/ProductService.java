package com.example.TradeTech.Service;

import com.example.TradeTech.Exceptions.ResourceNotFoundException;
import com.example.TradeTech.Model.Product;
import com.example.TradeTech.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAll() {
        try {
            return productRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to fetch products",e);
        }
    }

    public Product getById(Long id) {
        try {
            return productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to fetch products",e);
        }
    }

    public Product save(Product product) {
        try {
            return productRepository.save(product);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to save product",e);
        }
    }

    public void delete(Long id) {
        try {
            if(!productRepository.existsById(id)) {
                throw new ResourceNotFoundException("Product not found with id: " + id);
            }
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product",e);
        }
    }
}
