package com.pdp.currencyfetcher.adapter.repository;

import com.pdp.currencyfetcher.domain.RateEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<RateEntity, UUID> {

  List<RateEntity> findByCurrencyAndDateBetween(String currency, LocalDateTime from, LocalDateTime to);

}
