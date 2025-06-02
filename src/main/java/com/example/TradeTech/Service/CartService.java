package com.example.TradeTech.Service;

import com.example.TradeTech.Exceptions.ResourceNotFoundException;
import com.example.TradeTech.Model.Cart;
import com.example.TradeTech.Model.CartItem;
import com.example.TradeTech.Model.Product;
import com.example.TradeTech.Repository.CartRepository;
import com.example.TradeTech.Repository.CategoryRepository;
import com.example.TradeTech.Repository.ProductRepository;
import com.example.TradeTech.Repository.UserRepository;
import com.example.TradeTech.dto.CartItemDTO;
import com.example.TradeTech.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCartByUserId(Long userId) {
        try {
            return cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch cart",e);
        }
    }


    public Cart addItemToCart(Long userId, CartItemDTO cartItemDTO) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ userId));
            Product product = productRepository.findById(cartItemDTO.getProductId())
                    .orElseThrow(() ->new ResourceNotFoundException("Product not found with id: " + cartItemDTO.getProductId()));

            Cart cart = cartRepository.findByUserId(userId)
                    .orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setUser(user);
                        return cartRepository.save(newCart);
                    });
            Optional<CartItem> existingItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(cartItemDTO.getProductId())).findFirst();

            if(existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + cartItemDTO.getQuantity());
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(cartItem.getQuantity());
                cart.getCartItems().add(cartItem);
            }
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add item to cart", e);
        }
    }

    public void remoteItemFromCart(Long userId, Long productId){
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
            cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove item from cart",e);
        }
    }

    public void clearCart(Long userId) {
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user id: " + userId));
            cart.getCartItems().clear();
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear cart");
        }
    }
}
