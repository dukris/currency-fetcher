package com.pdp.currencyfetcher.adapter.impl;

import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import com.pdp.currencyfetcher.adapter.repository.RateRepository;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.domain.RateEntity;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.exception.NoUpdatedContentException;
import com.pdp.currencyfetcher.extensions.FakeRate;
import com.pdp.currencyfetcher.extensions.FakeRateEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatePersistenceAdapterImplTest {

    private final Long currentVersion = 1L;

    @Mock
    private RateRepository repository;
    @Mock
    private VersionPersistenceAdapter versionPersistenceAdapter;
    @Mock
    private RateMapper mapper;
    @InjectMocks
    private RatePersistenceAdapterImpl adapter;

    @Test
    @ExtendWith(FakeRate.class)
    void shouldSaveOrUpdateCurrenciesAndRates(Rate expected) {
        // given

        // when
        adapter.upsert(List.of(expected));

        // then
        verify(repository).upsert(expected.getCurrency(), expected.getValue());
        verify(versionPersistenceAdapter).next();
    }

    @Test
    @ExtendWith({FakeRate.class, FakeRateEntity.class})
    void shouldReturnAllRates(RateEntity entity, Rate expected) {
        // given
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toModel(List.of(entity))).thenReturn(List.of(expected));

        // when
        List<Rate> actual = adapter.getAll();

        // then
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
        verify(repository).findAll();
    }

    @Test
    @ExtendWith({FakeRate.class, FakeRateEntity.class})
    void shouldPollRatesIfProvidedVersionIsLower(RateEntity entity, Rate expected) {
        // given
        when(versionPersistenceAdapter.current()).thenReturn(currentVersion);
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toModel(List.of(entity))).thenReturn(List.of(expected));

        // when
        List<Rate> actual = adapter.poll(currentVersion - 1, 1L);

        // then
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
        verify(versionPersistenceAdapter).current();
        verify(repository).findAll();
    }

    @Test
    @ExtendWith({FakeRate.class, FakeRateEntity.class})
    void shouldPollUpdatedRatesAfterTimeoutIfProvidedVersionIsHigher(RateEntity entity, Rate expected) {
        // given
        when(versionPersistenceAdapter.current()).thenReturn(currentVersion, currentVersion + 1);
        when(mapper.toModel(List.of(entity))).thenReturn(List.of(expected));
        when(repository.findAll()).thenReturn(List.of(entity));

        // when
        List<Rate> actual = adapter.poll(currentVersion, 5L);

        // then
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
        verify(versionPersistenceAdapter, times(2)).current();
        verify(repository).findAll();
    }

    @Test
    void shouldThrowNoContentExceptionIfProvidedVersionIsHigher() {
        // given
        when(versionPersistenceAdapter.current()).thenReturn(currentVersion);

        // when
        Executable executable = () -> adapter.poll(currentVersion + 1, 1L);

        // then
        assertThrows(NoUpdatedContentException.class, executable);
    }

}
