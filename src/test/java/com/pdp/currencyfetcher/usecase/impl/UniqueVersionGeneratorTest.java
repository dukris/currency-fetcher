package com.pdp.currencyfetcher.usecase.impl;

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
class UniqueVersionGeneratorTest {

  private final Long expected = 1L;

  @Mock
  private JdbcTemplate template;
  @InjectMocks
  private UniqueVersionGenerator generator;

  @Test
  void shouldGenerateUniqueVersion() {
    // given
    when(template.queryForObject("SELECT nextval('version_no_seq')", Long.class)).thenReturn(expected);

    // when
    Long actual = generator.next();

    // then
    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(template).queryForObject("SELECT nextval('version_no_seq')", Long.class);
  }

  @Test
  void shouldReturnCurrentVersion() {
    // given
    when(template.queryForObject("SELECT currval('version_no_seq')", Long.class)).thenReturn(expected);

    // when
    Long actual = generator.current();

    // then
    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(template).queryForObject("SELECT currval('version_no_seq')", Long.class);
  }

}
