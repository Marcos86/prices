package com.mdelamo.prices.domain.repository;

import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;

import java.util.List;

public interface PriceRepository {

    List<Price> findAllByCriteria(FindPriceRequest findPriceRequest);
}
