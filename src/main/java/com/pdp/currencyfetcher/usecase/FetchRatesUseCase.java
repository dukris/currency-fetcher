package com.pdp.currencyfetcher.usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface FetchRatesUseCase {

  /**
   * Fetches currencies and rates using an appropriate API.
   *
   * @return List of currencies and rates
   */
  List<RateData> fetch();

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  class RateData {

    private String symbol;
    private BigDecimal price;
  }

}
