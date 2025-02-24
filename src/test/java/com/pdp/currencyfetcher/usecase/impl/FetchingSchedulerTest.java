package com.pdp.currencyfetcher.usecase.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.extensions.FakeRate;
import com.pdp.currencyfetcher.mapper.RateMapper;
import com.pdp.currencyfetcher.usecase.FetchRatesUseCase;
import com.pdp.currencyfetcher.usecase.FetchRatesUseCase.RateData;
import com.pdp.currencyfetcher.usecase.SaveCurrencyUseCase;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FetchingSchedulerTest {

  @Mock
  private FetchRatesUseCase fetcher;
  @Mock
  private SaveCurrencyUseCase saver;
  @Mock
  private RateMapper mapper;
  @Captor
  private ArgumentCaptor<List<Rate>> captor;
  @InjectMocks
  private FetchingScheduler scheduler;

  @Test
  @ExtendWith(FakeRate.class)
  void shouldFetchRates(Rate expected) {
    // given
    when(fetcher.fetch()).thenReturn(List.of(new RateData("USDT", new BigDecimal(1))));
    when(mapper.toEntity(anyList())).thenReturn(List.of(expected));

    // when
    scheduler.schedule();

    // then
    verify(fetcher).fetch();
    verify(saver).save(captor.capture());

    List<Rate> rates = captor.getValue();
    assertNotNull(rates);
    assertEquals(1, rates.size());
    assertEquals(expected, rates.get(0));
  }

}
