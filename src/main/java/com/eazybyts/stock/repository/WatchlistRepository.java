package com.eazybyts.stock.repository;

import com.eazybyts.stock.model.Watchlist;
import com.eazybyts.stock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUser(User user);
    boolean existsByUserAndTicker(User user, String ticker);
}