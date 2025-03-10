package com.pdp.currencyfetcher.gateway.client;

import com.pdp.currencyfetcher.api.dto.BinanceRateDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "binance")
public interface BinanceClient {

  /**
   * Gets currencies and rates.
   *
   * @return List of currencies and rates
   */
  @GetMapping("/api/v3/ticker/price")
  List<BinanceRateDto> getAll();

}
