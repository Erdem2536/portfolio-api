package com.example.portfolio.api;

import com.example.portfolio.application.AddHoldingService;
import com.example.portfolio.application.CreatePortfolioService;
import com.example.portfolio.application.GetPortfolioService;
import com.example.portfolio.domain.Portfolio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final CreatePortfolioService createService;
    private final GetPortfolioService getService;
    private final AddHoldingService addHoldingService;

    public PortfolioController(
            CreatePortfolioService createService,
            GetPortfolioService getService,
            AddHoldingService addHoldingService
    ) {
        this.createService = createService;
        this.getService = getService;
        this.addHoldingService = addHoldingService;
    }

    // ----- Create portfolio -----
    public record CreatePortfolioRequest(
            @NotBlank String name
    ) {}

    public record CreatePortfolioResponse(
            UUID id,
            String name
    ) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePortfolioResponse create(@Valid @RequestBody CreatePortfolioRequest req) {
        Portfolio created = createService.create(req.name());
        return new CreatePortfolioResponse(created.getId(), created.getName());
    }

    // ----- Get portfolio with holdings -----
    public record HoldingResponse(
            String symbol,
            String quantity
    ) {}

    public record GetPortfolioResponse(
            UUID id,
            String name,
            List<HoldingResponse> holdings
    ) {}

    @GetMapping("/{id}")
    public GetPortfolioResponse getById(@PathVariable("id") UUID id) {
        GetPortfolioService.PortfolioView view = getService.getViewById(id);

        List<HoldingResponse> holdings = view.holdings().stream()
                .map(h -> new HoldingResponse(h.symbol(), h.quantity()))
                .toList();

        return new GetPortfolioResponse(
                view.id(),
                view.name(),
                holdings
        );
    }

    // ----- Add holding -----
    public record AddHoldingRequest(
            @NotBlank String symbol,

            @NotNull(message = "quantity is required")
            @DecimalMin(
                    value = "0.0001",
                    inclusive = true,
                    message = "quantity must be greater than 0"
            )
            BigDecimal quantity
    ) {}

    @PostMapping("/{id}/holdings")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addHolding(
            @PathVariable("id") UUID id,
            @Valid @RequestBody AddHoldingRequest req
    ) {
        addHoldingService.addOrUpdate(
                id,
                req.symbol(),
                req.quantity()
        );
    }
}
