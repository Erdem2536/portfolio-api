package com.example.portfolio.infrastructure;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class HoldingId implements Serializable {

    private UUID portfolioId;
    private String symbol;

    protected HoldingId() {}

    public HoldingId(UUID portfolioId, String symbol) {
        this.portfolioId = portfolioId;
        this.symbol = symbol;
    }

    public UUID getPortfolioId() { return portfolioId; }
    public String getSymbol() { return symbol; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HoldingId other)) return false;
        return Objects.equals(portfolioId, other.portfolioId)
                && Objects.equals(symbol, other.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, symbol);
    }
}
