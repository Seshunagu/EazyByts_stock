package com.eazybyts.stock.controller;

import com.eazybyts.stock.model.Trade;
import com.eazybyts.stock.model.User;
import com.eazybyts.stock.repository.UserRepository;
import com.eazybyts.stock.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trades")
@CrossOrigin
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserRepository userRepository;

    // --- Execute Trade ---
    @PostMapping("/execute")
    public ResponseEntity<?> executeTrade(@RequestBody TradeRequest request) {
        try {
            // Find user
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Execute trade
            Trade trade = tradeService.executeTrade(
                    user,
                    request.getTicker(),
                    request.getQuantity(),
                    request.getPrice(),
                    request.getType()
            );

            // Return trade as JSON
            return ResponseEntity.ok(trade);

        } catch (RuntimeException e) {
            // Known errors (e.g., insufficient holdings)
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            // Internal server errors
            return ResponseEntity.status(500).body(new ErrorResponse("Internal server error"));
        }
    }

    // --- DTO for JSON request ---
    public static class TradeRequest {
        private Long userId;
        private String ticker;
        private int quantity;
        private double price;
        private String type;

        // Getters and setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public String getTicker() { return ticker; }
        public void setTicker(String ticker) { this.ticker = ticker; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    // --- DTO for error response ---
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) { this.message = message; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
