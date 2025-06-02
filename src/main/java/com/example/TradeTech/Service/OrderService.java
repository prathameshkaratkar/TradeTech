package com.example.TradeTech.Service;

import com.example.TradeTech.Exceptions.ResourceNotFoundException;
import com.example.TradeTech.Model.Cart;
import com.example.TradeTech.Model.Order;
import com.example.TradeTech.Model.OrderItem;
import com.example.TradeTech.Model.User;
import com.example.TradeTech.Repository.OrderRepository;
import com.example.TradeTech.Repository.UserRepository;
import com.example.TradeTech.dto.OrderDTO;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    public Order createOrder(OrderDTO orderDTO) {
        try {
            User user = userRepository.findById(orderDTO.userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+ orderDTO.userId));
            Cart cart = cartService.getCartByUserId(orderDTO.userId);

            if(cart.getCartItems().isEmpty()) {
                throw new IllegalArgumentException("Cannot create order with empty");
            }

            Order order = new Order();
            order.setUser(user);
            order.setOrderDate(LocalDateTime.now());


            double totalAmount = 0.0;
            for(var cartItem: cart.getCartItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
                order.getOrderItems().add(orderItem);
                totalAmount += cartItem.getProduct().getPrice()* cartItem.getQuantity();
            }

            order.setTotalAmount(totalAmount);

            Order savedOrder = orderRepository.save(order);
            cartService.clearCart(user.getId());
            return savedOrder;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create order",e);
        }
    }

    public List<Order> getOrdersByUserId(Long userId) {
        try {
            return orderRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch orders",e);
        }
    }

    public Order getOrderById(Long id) {
        try {
            return orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch order",e);
        }
    }
}
