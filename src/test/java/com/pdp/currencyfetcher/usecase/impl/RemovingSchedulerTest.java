package com.pdp.currencyfetcher.usecase.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RemovingSchedulerTest {

    @Mock
    private RatePersistenceAdapter ratePersistenceAdapter;
    @InjectMocks
    private RemovingScheduler scheduler;

    @Test
    void shouldFetchRates() {
        // given

        // when
        scheduler.remove();

        // then
        verify(ratePersistenceAdapter).deleteByDateBefore(any(LocalDateTime.class));
    }

}
