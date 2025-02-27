package com.pdp.currencyfetcher.usecase.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.domain.RateEntity;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.extensions.FakeRateData;
import com.pdp.currencyfetcher.extensions.FakeRateEntity;
import com.pdp.currencyfetcher.gateway.BinanceGateway;
import com.pdp.currencyfetcher.gateway.BinanceGateway.RateData;
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
  private BinanceGateway binanceGateway;
  @Mock
  private RatePersistenceAdapter ratePersistenceAdapter;
  @Mock
  private RateMapper mapper;
  @Captor
  private ArgumentCaptor<List<RateEntity>> captor;
  @InjectMocks
  private FetchingScheduler scheduler;

  @Test
  @ExtendWith({FakeRateEntity.class, FakeRateData.class})
  void shouldFetchRates(RateEntity expected, RateData rate) {
    // given
    when(binanceGateway.getAll()).thenReturn(List.of(rate));
    when(mapper.toEntity(anyList())).thenReturn(List.of(expected));

    // when
    scheduler.schedule();

    // then
    verify(binanceGateway).getAll();
    verify(ratePersistenceAdapter).save(captor.capture());

    List<RateEntity> rates = captor.getValue();
    assertNotNull(rates);
    assertEquals(1, rates.size());
    assertEquals(expected, rates.get(0));
  }

}
