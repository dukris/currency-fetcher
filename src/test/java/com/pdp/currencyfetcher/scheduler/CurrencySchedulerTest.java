package com.pdp.currencyfetcher.scheduler;

import static org.mockito.Mockito.verify;

import com.pdp.currencyfetcher.usecase.FetchRatesUseCase;
import com.pdp.currencyfetcher.usecase.FetchRatesUseCase.RateData;
import com.pdp.currencyfetcher.usecase.SaveCurrencyUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencySchedulerTest {

  @Mock
  private FetchRatesUseCase fetcher;

  @Mock
  private SaveCurrencyUseCase saver;

  @Captor
  private ArgumentCaptor<RateData> rateDataCaptor;

  @InjectMocks
  private CurrencyScheduler scheduler;

  @Test
  void shouldFetchRates() {
    // given

    // when
    scheduler.schedule();

    // then
    verify(fetcher).fetch();
  }

}
