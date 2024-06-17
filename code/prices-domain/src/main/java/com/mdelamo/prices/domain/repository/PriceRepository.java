package com.mdelamo.prices.domain.repository;

import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;

import java.util.List;
import java.util.Optional;

public interface PriceRepository {

    List<Price> findAllByCriteria(FindPriceRequest findPriceRequest);
}
