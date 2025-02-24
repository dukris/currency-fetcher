package com.pdp.currencyfetcher.usecase.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.extensions.FakeRateData;
import com.pdp.currencyfetcher.usecase.FetchRatesUseCase.RateData;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class BinanceRatesFetcherTest {

  @Mock
  private RestTemplate template;
  @InjectMocks
  private BinanceRatesFetcher fetcher;

  @Test
  @ExtendWith(FakeRateData.class)
  void shouldFetchRates(RateData expected) {
    // given
    when(template.getForObject(anyString(), any())).thenReturn(new RateData[]{expected});

    // when
    List<RateData> actual = fetcher.fetch();

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected, actual.get(0));
    verify(template).getForObject(anyString(), any());
  }

}
