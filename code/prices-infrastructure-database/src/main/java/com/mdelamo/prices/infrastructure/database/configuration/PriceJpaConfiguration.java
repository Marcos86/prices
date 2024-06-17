package com.mdelamo.prices.infrastructure.database.configuration;

import com.mdelamo.prices.infrastructure.database.entity.PriceEntity;
import com.mdelamo.prices.infrastructure.database.repository.PriceJpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {PriceJpaRepository.class})
@EntityScan(basePackageClasses = {PriceEntity.class})
public class PriceJpaConfiguration {

}
