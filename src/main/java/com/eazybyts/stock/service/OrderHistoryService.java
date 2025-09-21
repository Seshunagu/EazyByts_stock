package com.eazybyts.stock.service;

import com.eazybyts.stock.model.OrderHistory;
import com.eazybyts.stock.model.User;
import com.eazybyts.stock.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    public List<OrderHistory> getUserOrderHistory(User user) {
        return orderHistoryRepository.findByUser(user);
    }
}