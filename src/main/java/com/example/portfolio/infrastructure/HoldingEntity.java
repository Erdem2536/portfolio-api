package com.example.portfolio.infrastructure;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "holdings")
public class HoldingEntity {

    @EmbeddedId
    private HoldingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("portfolioId")
    @JoinColumn(name = "portfolio_id", nullable = false)
    private PortfolioEntity portfolio;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected HoldingEntity() {

    }

    public HoldingEntity(PortfolioEntity portfolio, String symbol, BigDecimal quantity, Instant createdAt) {
        this.portfolio = portfolio;
        this.id = new HoldingId(portfolio.getId(), symbol);
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public HoldingId getId() { return id; }


    public String getSymbol() { return id.getSymbol(); }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public Instant getCreatedAt() { return createdAt; }
}
