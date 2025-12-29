package com.example.portfolio.application;

import com.example.portfolio.domain.Portfolio;
import com.example.portfolio.infrastructure.PortfolioEntity;
import com.example.portfolio.infrastructure.PortfolioJpaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class CreatePortfolioService {

    private final PortfolioJpaRepository repo;

    public CreatePortfolioService(PortfolioJpaRepository repo) {
        this.repo = repo;
    }

    public Portfolio create(String name) {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        PortfolioEntity entity = new PortfolioEntity(id, name, now);
        repo.save(entity);

        return new Portfolio(id, name, now);
    }
}
