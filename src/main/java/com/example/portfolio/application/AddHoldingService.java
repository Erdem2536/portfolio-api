package com.example.portfolio.application;

import com.example.portfolio.infrastructure.HoldingEntity;
import com.example.portfolio.infrastructure.PortfolioEntity;
import com.example.portfolio.infrastructure.PortfolioJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddHoldingService {

    private final PortfolioJpaRepository repo;

    public AddHoldingService(PortfolioJpaRepository repo) {
        this.repo = repo;
    }


    @Transactional
    public void addOrUpdate(UUID portfolioId, String symbol, BigDecimal quantity) {
        PortfolioEntity portfolio = repo.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found: " + portfolioId));

        Optional<HoldingEntity> existing = portfolio.getHoldings().stream()
                .filter(h -> h.getSymbol().equalsIgnoreCase(symbol))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(quantity); // istersen: existing.get().setQuantity(existing.get().getQuantity().add(quantity));
        } else {
            portfolio.getHoldings().add(new HoldingEntity(
                    portfolio,
                    symbol,
                    quantity,
                    Instant.now()
            ));
        }

        repo.save(portfolio);
    }
}
