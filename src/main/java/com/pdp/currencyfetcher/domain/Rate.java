package com.pdp.currencyfetcher.domain;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

  private UUID id;
  private String currency;
  private BigDecimal rate;

}
