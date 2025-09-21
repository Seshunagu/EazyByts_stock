package com.eazybyts.stock.controller;

import com.eazybyts.stock.model.OrderHistory;
import com.eazybyts.stock.model.User;
import com.eazybyts.stock.repository.UserRepository;
import com.eazybyts.stock.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public List<OrderHistory> getUserOrders(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return orderHistoryService.getUserOrderHistory(user);
    }
}