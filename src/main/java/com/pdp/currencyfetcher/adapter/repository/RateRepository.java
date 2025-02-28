package com.pdp.currencyfetcher.adapter.repository;

import com.pdp.currencyfetcher.domain.Rate;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, UUID> {
}
