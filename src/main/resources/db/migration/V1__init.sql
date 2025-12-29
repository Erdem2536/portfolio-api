CREATE TABLE portfolios (
                            id UUID PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE holdings (
                          portfolio_id UUID NOT NULL,
                          symbol VARCHAR(20) NOT NULL,
                          quantity NUMERIC(19, 4) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          PRIMARY KEY (portfolio_id, symbol),
                          CONSTRAINT fk_holdings_portfolio
                              FOREIGN KEY (portfolio_id)
                                  REFERENCES portfolios (id)
                                  ON DELETE CASCADE
);
