package com.pdp.currencyfetcher.adapter.repository;

import com.pdp.currencyfetcher.domain.RateEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RateRepository extends JpaRepository<RateEntity, UUID> {

  @Query(
      value = """
          SELECT DISTINCT ON (currency) *
          FROM fetcher.rates
          ORDER BY currency, date DESC
          """,
      nativeQuery = true
  )
  List<RateEntity> findLatestRates();

  List<RateEntity> findByCurrencyAndDateBetween(String currency, LocalDateTime from, LocalDateTime to);

  void deleteAllByDateBefore(LocalDateTime date);

}
