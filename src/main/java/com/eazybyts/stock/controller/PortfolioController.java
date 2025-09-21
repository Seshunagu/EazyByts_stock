package com.eazybyts.stock.controller;

import com.eazybyts.stock.model.Portfolio;
import com.eazybyts.stock.model.User;
import com.eazybyts.stock.repository.UserRepository;
import com.eazybyts.stock.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
            Portfolio portfolio = portfolioService.getPortfolio(user);
            return ResponseEntity.ok(portfolio);
        } catch (Exception e) {
            System.err.println("Error fetching portfolio for user " + userId + ": " + e.getMessage());
            return ResponseEntity.ok(new Portfolio()); // Return empty portfolio on error
        }
    }
}
