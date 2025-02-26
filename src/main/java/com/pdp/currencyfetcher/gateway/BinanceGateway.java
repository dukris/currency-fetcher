package com.pdp.currencyfetcher.gateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface BinanceGateway {

  /**
   * Gets currencies and rates.
   *
   * @return List of currencies and rates
   */
  List<RateData> getAll();

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  class RateData {

    private String symbol;
    private BigDecimal price;
  }

}
