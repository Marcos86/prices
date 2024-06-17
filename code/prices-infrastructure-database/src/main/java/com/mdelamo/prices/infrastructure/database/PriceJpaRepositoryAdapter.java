package com.mdelamo.prices.infrastructure.database;

import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.domain.repository.PriceRepository;
import com.mdelamo.prices.infrastructure.database.mapper.JpaPriceMapper;
import com.mdelamo.prices.infrastructure.database.repository.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceJpaRepositoryAdapter implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;

    private final JpaPriceMapper jpaPriceMapper;

    @Override
    public List<Price> findAllByCriteria(final FindPriceRequest findPriceRequest) {
        var productId = Long.parseLong(findPriceRequest.getProductId());
        var brandId = Long.parseLong(findPriceRequest.getBrandId());
        var priceDateTime = findPriceRequest.getPriceDateTime();

        return this.priceJpaRepository
                .findAllByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(productId, brandId, priceDateTime, priceDateTime)
                .stream()
                .map(this.jpaPriceMapper::map)
                .toList();
    }
}
