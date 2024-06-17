package com.mdelamo.prices.domain.usecase;

import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;

public interface PriceUseCase {

    Price findPrice(FindPriceRequest findPriceRequest);
}
