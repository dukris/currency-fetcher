package com.pdp.currencyfetcher.adapter.repository;

import com.pdp.currencyfetcher.domain.Rate;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RateRepository extends JpaRepository<Rate, UUID> {

  @Modifying
  @Query(value = """
          INSERT INTO fetcher.rates (id, currency, value, date)
          VALUES (gen_random_uuid(), :currency, :value, CURRENT_TIMESTAMP)
          ON CONFLICT (currency)
          DO UPDATE SET value = EXCLUDED.value, date = CURRENT_TIMESTAMP
      """, nativeQuery = true)
  void upsert(@Param("currency") String currency, @Param("value") BigDecimal value);

}
