package com.eazybyts.stock.service;

import com.eazybyts.stock.model.Holding;
import com.eazybyts.stock.model.Portfolio;
import com.eazybyts.stock.model.User;
import com.eazybyts.stock.repository.HoldingRepository;
import com.eazybyts.stock.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    public Portfolio getPortfolio(User user) {
        List<Holding> holdings = holdingRepository.findByUser(user);

        double cash = user.getCash() != null ? user.getCash() : 0.0;
        double totalValue = holdings.stream()
                .mapToDouble(h -> h.getQuantity() * h.getAvgPrice())
                .sum() + cash;

        Portfolio portfolio = portfolioRepository.findByUser(user);
        if (portfolio == null) {
            portfolio = new Portfolio();
            portfolio.setUser(user);
        }

        portfolio.setTotalValue(totalValue);
        portfolio.setHoldings(holdings); // include holdings for frontend

        return portfolioRepository.save(portfolio);
    }
}
