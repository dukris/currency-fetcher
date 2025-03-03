package com.pdp.currencyfetcher.api;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import com.pdp.currencyfetcher.api.dto.RateDto;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.extensions.FakeRate;
import com.pdp.currencyfetcher.extensions.FakeRateDto;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(RateController.class)
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RateControllerTest {

  private final Long version = 1L;
  private final Long timeout = 0L;

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
  @ExtendWith({FakeRate.class, FakeRateDto.class})
  void shouldReturnRates(Rate rate, RateDto dto) throws Exception {
    // given
    when(versionPersistenceAdapter.next()).thenReturn(version + 1);
    when(ratePersistenceAdapter.poll(version, timeout)).thenReturn(List.of(rate));
    when(mapper.toDto(List.of(rate))).thenReturn(List.of(dto));

    // when
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/v1/rates")
            .param("version", version.toString())
            .param("timeout", timeout.toString()))
        .andExpect(request().asyncStarted())
        .andReturn();

    // then
    mvc.perform(asyncDispatch(mvcResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.version").value(2))
        .andExpect(jsonPath("$.rates[0].currency").value("USD/EUR"))
        .andExpect(jsonPath("$.rates[0].rate").value(1.12));
  }

  @Test
  void shouldReturnError() throws Exception {
    // given
    when(versionPersistenceAdapter.current()).thenThrow(new RuntimeException());

    // when
    mvc.perform(MockMvcRequestBuilders.get("/api/v1/rates")
            .param("version", version.toString())
            .param("timeout", timeout.toString())
            .contentType(APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("Error while fetching rates"));
  }

  @Test
  void shouldReturnNoContent() throws Exception {
    // given
    when(versionPersistenceAdapter.next()).thenThrow(new RuntimeException());

    // when
    mvc.perform(MockMvcRequestBuilders.get("/api/v1/rates")
            .param("version", version.toString())
            .param("timeout", timeout.toString())
            .contentType(APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }


}
