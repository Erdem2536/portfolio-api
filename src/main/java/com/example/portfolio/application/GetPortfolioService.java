package com.example.portfolio.application;

import com.example.portfolio.infrastructure.PortfolioEntity;
import com.example.portfolio.infrastructure.PortfolioJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GetPortfolioService {

    private final PortfolioJpaRepository repo;

    public GetPortfolioService(PortfolioJpaRepository repo) {
        this.repo = repo;
    }

    public record HoldingView(String symbol, String quantity) {}
    public record PortfolioView(UUID id, String name, List<HoldingView> holdings) {}

    /**
     * IMPORTANT:
     * open-in-view is disabled (spring.jpa.open-in-view=false),
     * and holdings is a LAZY relationship.
     *
     * Without an active transaction, accessing entity.getHoldings()
     * can trigger a LazyInitializationException and result in HTTP 500.
     *
     * This read-only transaction keeps the persistence context open
     * while we map holdings into DTOs.
     */
    @Transactional(readOnly = true)
    public PortfolioView getViewById(UUID id) {
        PortfolioEntity entity = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found: " + id));

        List<HoldingView> holdings = entity.getHoldings().stream()
                .map(h -> new HoldingView(h.getSymbol(), h.getQuantity().toPlainString()))
                .toList();

        return new PortfolioView(entity.getId(), entity.getName(), holdings);
    }
}
