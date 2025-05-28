package com.pdp.currencyfetcher.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import com.pdp.currencyfetcher.api.dto.RateDto;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.extensions.FakeRate;
import com.pdp.currencyfetcher.extensions.FakeRateDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(controllers = RateController.class)
class RateControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private VersionPersistenceAdapter versionPersistenceAdapter;
  @MockBean
  private RatePersistenceAdapter ratePersistenceAdapter;
  @MockBean
  private ExecutorService executor;
  @MockBean
  private RateMapper mapper;

  @Test
  @ExtendWith({FakeRateDto.class, FakeRate.class})
  void shouldReturnAllRates(Rate rate, RateDto dto) throws Exception {
    // given
    when(ratePersistenceAdapter.getAll()).thenReturn(List.of(rate));
    when(mapper.toDto(List.of(rate))).thenReturn(List.of(dto));

    // when
    this.mvc.perform(get("/api/v1/rates")).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value(dto.getId().toString()))
        .andExpect(jsonPath("$[0].currency").value(dto.getCurrency()))
        .andExpect(jsonPath("$[0].value").value(dto.getValue()));
  }

  @Test
  @ExtendWith({FakeRateDto.class, FakeRate.class})
  void shouldReturnAllRatesByCurrencyAndDate(Rate rate, RateDto dto) throws Exception {
    // given
    String currency = "USD";
    LocalDateTime from = LocalDateTime.now();
    LocalDateTime to = from.plusDays(1);
    when(ratePersistenceAdapter.getByPeriod(currency, from, to)).thenReturn(List.of(rate));
    when(mapper.toDto(List.of(rate))).thenReturn(List.of(dto));

    // when
    this.mvc.perform(get("/api/v1/rates/currencies")
            .param("currency", currency)
            .param("from", from.toString())
            .param("to", to.toString())
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value(dto.getId().toString()))
        .andExpect(jsonPath("$[0].currency").value(dto.getCurrency()))
        .andExpect(jsonPath("$[0].value").value(dto.getValue()));
  }

}
