package com.pdp.currencyfetcher.gateway;

import com.pdp.currencyfetcher.api.dto.BinanceRateDto;
import com.pdp.currencyfetcher.gateway.client.BinanceClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BinanceGatewayImpl implements BinanceGateway {

  private final BinanceClient client;

  @Override
  public List<BinanceRateDto> getAll() {
    return client.getAll();
  }

}
