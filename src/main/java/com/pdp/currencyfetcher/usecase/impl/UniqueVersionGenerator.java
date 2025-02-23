package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.usecase.GenerateVersionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueVersionGenerator implements GenerateVersionUseCase {

    private final JdbcTemplate template;

    @Override
    public Long next() {
        return template.queryForObject("SELECT nextval('version_no_seq')", Long.class);
    }

    @Override
    public Long current() {
        return template.queryForObject("SELECT currval('version_no_seq')", Long.class);
    }
}
