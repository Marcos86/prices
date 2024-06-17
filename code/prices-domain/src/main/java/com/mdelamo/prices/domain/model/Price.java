package com.mdelamo.prices.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    private String id;
    private String brandId;
    private String productId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long priceList;
    private Integer priority;
    @SuppressWarnings("java:S1700")// Suppress advice due to we want this name for attribute
    private BigDecimal price;
    private String currency;

}
