package com.eazybyts.stock.controller;

import com.eazybyts.stock.model.Holding;
import com.eazybyts.stock.model.User;
import com.eazybyts.stock.repository.HoldingRepository;
import com.eazybyts.stock.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holdings")
@CrossOrigin
public class HoldingController {

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public List<Holding> getUserHoldings(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return holdingRepository.findByUser(user);
    }
}