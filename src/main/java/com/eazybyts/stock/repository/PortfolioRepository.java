package com.eazybyts.stock.repository;

import com.eazybyts.stock.model.Portfolio;
import com.eazybyts.stock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Portfolio findByUser(User user);
}
