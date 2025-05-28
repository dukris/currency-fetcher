package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.api.dto.BinanceRateDto;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.extensions.FakeBinanceRateDto;
import com.pdp.currencyfetcher.extensions.FakeRate;
import com.pdp.currencyfetcher.gateway.BinanceGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchingSchedulerTest {

    @Mock
    private BinanceGateway binanceGateway;
    @Mock
    private RatePersistenceAdapter ratePersistenceAdapter;
    @Mock
    private RateMapper mapper;
    @Captor
    private ArgumentCaptor<List<Rate>> captor;
    @InjectMocks
    private FetchingScheduler scheduler;

    @Test
    @ExtendWith({FakeRate.class, FakeBinanceRateDto.class})
    void shouldFetchRates(Rate expected, BinanceRateDto rate) {
        // given
        when(binanceGateway.getAll()).thenReturn(List.of(rate));
        when(mapper.toModels(anyList())).thenReturn(List.of(expected));

        // when
        scheduler.schedule();

        // then
        verify(binanceGateway).getAll();
        verify(ratePersistenceAdapter).save(captor.capture());

        List<Rate> rates = captor.getValue();
        assertNotNull(rates);
        assertEquals(1, rates.size());
        assertEquals(expected, rates.get(0));
    }

}
