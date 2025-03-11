package com.pdp.currencyfetcher.adapter.impl;

import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VersionPersistenceAdapterImpl implements VersionPersistenceAdapter {

  private final JdbcTemplate template;

  @Override
  public Long next() {
    return template.queryForObject("SELECT nextval('version_no_seq')", Long.class);
  }

  @Override
  public Long current() {
    return template.queryForObject("SELECT last_value FROM version_no_seq", Long.class) - 1;
  }

}
