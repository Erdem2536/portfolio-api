package com.example.portfolio.domain;

import java.time.Instant;
import java.util.UUID;

public class Portfolio {
    private UUID id;
    private String name;
    private Instant createdAt;

    public Portfolio(UUID id, String name, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public Instant getCreatedAt() { return createdAt; }
}
