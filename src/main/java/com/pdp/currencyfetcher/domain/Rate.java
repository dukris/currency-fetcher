package com.pdp.currencyfetcher.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    private UUID id;
    private String currency;
    private BigDecimal value;
    private LocalDateTime date;

}
