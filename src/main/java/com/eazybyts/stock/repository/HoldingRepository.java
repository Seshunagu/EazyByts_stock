package com.eazybyts.stock.repository;

import com.eazybyts.stock.model.Holding;
import com.eazybyts.stock.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
    List<Holding> findByUser(User user);
    Holding findByUserAndTicker(User user, String ticker);
}