package com.mdelamo.prices.application;

import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.domain.repository.PriceRepository;
import com.mdelamo.prices.domain.usecase.PriceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceUseCaseImpl implements PriceUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price findPrice(final FindPriceRequest findPriceRequest) {
        return this.priceRepository
                .findAllByCriteria(findPriceRequest)
                .stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElse(null);
    }
}
