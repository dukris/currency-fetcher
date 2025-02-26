package com.pdp.currencyfetcher.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.extensions.FakeRateData;
import com.pdp.currencyfetcher.gateway.BinanceGateway.RateData;
import com.pdp.currencyfetcher.gateway.client.BinanceClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BinanceGatewayImplTest {

  @Mock
  private BinanceClient client;
  @InjectMocks
  private BinanceGatewayImpl gateway;

  @Test
  @ExtendWith(FakeRateData.class)
  void shouldGetAllRates(RateData expected) {
    // given
    when(client.getAll()).thenReturn(List.of(expected));

    // when
    List<RateData> actual = gateway.getAll();

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected, actual.get(0));
  }

}
