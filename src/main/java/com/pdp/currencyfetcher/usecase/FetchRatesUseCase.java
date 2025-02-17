package com.pdp.currencyfetcher.usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface FetchRatesUseCase {

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
