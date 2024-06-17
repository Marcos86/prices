package com.mdelamo.prices.infrastructure.database.repository;

import com.mdelamo.prices.infrastructure.database.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    List<PriceEntity> findAllByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(final Long productId, final Long brandId, final LocalDateTime startDate, final LocalDateTime endDate);
}
