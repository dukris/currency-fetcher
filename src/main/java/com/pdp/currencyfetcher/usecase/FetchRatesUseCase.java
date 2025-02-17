package com.pdp.currencyfetcher.usecase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pdp.currencyfetcher.domain.Rate;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface FetchRatesUseCase {

  CompletableFuture<List<RateData>> fetch();

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  class RateData {

    private String symbol;
    private BigDecimal price;
  }

  @Mapper
  interface RateDataMapper {

    @Mapping(target = "currency", source = "symbol")
    @Mapping(target = "rate", source = "price")
    Rate map(RateData data);
  }

}
