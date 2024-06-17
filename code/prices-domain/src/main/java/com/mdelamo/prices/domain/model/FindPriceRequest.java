package com.mdelamo.prices.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindPriceRequest {

    private LocalDateTime priceDateTime;

    private String productId;

    private String brandId;

}
