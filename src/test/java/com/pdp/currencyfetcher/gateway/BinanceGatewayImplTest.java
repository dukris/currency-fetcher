package com.pdp.currencyfetcher.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.api.dto.BinanceRateDto;
import com.pdp.currencyfetcher.extensions.FakeBinanceRateDto;
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
  @ExtendWith(FakeBinanceRateDto.class)
  void shouldGetAllRates(BinanceRateDto expected) {
    // given
    when(client.getAll()).thenReturn(List.of(expected));

    // when
    List<BinanceRateDto> actual = gateway.getAll();

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected, actual.get(0));
  }

}
