package com.pdp.currencyfetcher.gateway;

import com.pdp.currencyfetcher.api.dto.BinanceRateDto;
import java.util.List;

public interface BinanceGateway {

  /**
   * Gets currencies and rates.
   *
   * @return List of currencies and rates
   */
  List<BinanceRateDto> getAll();

}
