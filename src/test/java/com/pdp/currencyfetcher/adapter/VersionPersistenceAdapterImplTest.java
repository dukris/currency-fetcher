package com.pdp.currencyfetcher.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class VersionPersistenceAdapterImplTest {

  @Mock
  private JdbcTemplate template;
  @InjectMocks
  private VersionPersistenceAdapterImpl adapter;

  @Test
  void shouldGenerateUniqueVersion() {
    // given
    Long expected = 1L;
    when(template.queryForObject("SELECT nextval('version_no_seq')", Long.class)).thenReturn(expected);

    // when
    Long actual = adapter.next();

    // then
    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(template).queryForObject("SELECT nextval('version_no_seq')", Long.class);
  }

  @Test
  void shouldReturnCurrentVersion() {
    // given
    Long expected = 1L;
    when(template.queryForObject("SELECT currval('version_no_seq')", Long.class)).thenReturn(expected);

    // when
    Long actual = adapter.current();

    // then
    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(template).queryForObject("SELECT currval('version_no_seq')", Long.class);
  }

}
