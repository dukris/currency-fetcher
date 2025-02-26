package com.pdp.currencyfetcher.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@EnableSchedulerLock(defaultLockAtMostFor = "PT5S")
public class SchedulerConfig {

  private final DataSource dataSource;

  @Bean
  public LockProvider lockProvider() {
    return new JdbcTemplateLockProvider(dataSource);
  }

}
