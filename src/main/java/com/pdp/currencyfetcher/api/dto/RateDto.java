package com.pdp.currencyfetcher.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateDto {

    @Schema(name = "Unique identifier of the rate")
    private UUID id;

    @Schema(name = "Currency symbol")
    private String currency;

    @Schema(name = "Rate of the currency")
    private BigDecimal value;

}
