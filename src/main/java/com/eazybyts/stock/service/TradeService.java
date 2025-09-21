package com.eazybyts.stock.service;

import com.eazybyts.stock.model.*;
import com.eazybyts.stock.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Trade executeTrade(User user, String ticker, int quantity, double price, String type) {
        Trade trade = new Trade();
        trade.setTicker(ticker);
        trade.setQuantity(quantity);
        trade.setPrice(price);
        trade.setType(type);
        trade.setTimestamp(LocalDateTime.now());
        trade.setUser(user);
        tradeRepository.save(trade);

        // Update holdings
        Holding holding = holdingRepository.findByUserAndTicker(user, ticker);
        if (type.equalsIgnoreCase("BUY")) {
            if (holding == null) {
                holding = new Holding();
                holding.setUser(user);
                holding.setTicker(ticker);
                holding.setQuantity(quantity);
                holding.setAvgPrice(price);
            } else {
                int totalQty = holding.getQuantity() + quantity;
                double newAvg = ((holding.getQuantity() * holding.getAvgPrice()) + (quantity * price)) / totalQty;
                holding.setQuantity(totalQty);
                holding.setAvgPrice(newAvg);
            }
            holdingRepository.save(holding);
            user.setCash(user.getCash() - (quantity * price));
        } else if (type.equalsIgnoreCase("SELL")) {
            if (holding != null && holding.getQuantity() >= quantity) {
                holding.setQuantity(holding.getQuantity() - quantity);
                if (holding.getQuantity() == 0) {
                    holdingRepository.delete(holding);
                } else {
                    holdingRepository.save(holding);
                }
                user.setCash(user.getCash() + (quantity * price));
            } else {
                throw new RuntimeException("Not enough holdings to sell");
            }
        }

        userRepository.save(user);

        // Save to OrderHistory
        OrderHistory order = new OrderHistory();
        order.setUser(user);
        order.setTicker(ticker);
        order.setQuantity(quantity);
        order.setPrice(price);
        order.setType(type);
        order.setTimestamp(LocalDateTime.now());
        orderHistoryRepository.save(order);

        return trade;
    }
}