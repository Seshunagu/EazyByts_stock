package com.eazybyts.stock.controller;

import com.eazybyts.stock.model.User;
import com.eazybyts.stock.model.Watchlist;
import com.eazybyts.stock.repository.UserRepository;
import com.eazybyts.stock.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin
public class WatchlistController {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public List<String> getWatchlist(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return watchlistRepository.findByUser(user).stream()
                .map(Watchlist::getTicker)
                .collect(Collectors.toList());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addToWatchlist(@PathVariable Long userId, @RequestBody Watchlist request) {
        User user = userRepository.findById(userId).orElseThrow();

        if (watchlistRepository.existsByUserAndTicker(user, request.getTicker())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Ticker already in watchlist"));
        }

        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        watchlist.setTicker(request.getTicker());
        watchlistRepository.save(watchlist);

        return ResponseEntity.ok(Map.of(
            "message", "Ticker added successfully",
            "ticker", watchlist.getTicker()
        ));
    }

}