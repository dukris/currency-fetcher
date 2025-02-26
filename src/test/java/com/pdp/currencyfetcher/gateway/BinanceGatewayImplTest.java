package com.pdp.currencyfetcher.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.gateway.BinanceGateway.RateData;
import com.pdp.currencyfetcher.extensions.FakeRateData;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class BinanceGatewayImplTest {

  @Mock
  private RestTemplate template;
  @InjectMocks
  private BinanceGatewayImpl gateway;

  @Test
  @ExtendWith(FakeRateData.class)
  void shouldGetAllRates(RateData expected) {
    // given
    when(template.getForObject(anyString(), any())).thenReturn(new RateData[]{expected});

    // when
    List<RateData> actual = gateway.getAll();

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected, actual.get(0));
    verify(template).getForObject(anyString(), any());
  }

}
