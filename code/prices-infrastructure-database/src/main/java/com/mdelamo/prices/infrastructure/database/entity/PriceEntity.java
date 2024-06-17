package com.mdelamo.prices.infrastructure.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Price")
@Data
public class PriceEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_id", nullable = false, updatable = false)
    private Long productId;

    @Column(name = "brand_id", nullable = false, updatable = false)
    private Long brandId;

    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false, updatable = false)
    private LocalDateTime endDate;

    @Column(name = "price_list", nullable = false, updatable = false)
    private Long priceList;

    @Column(name = "priority", nullable = false, updatable = false)
    private int priority;

    @Column(name = "price", nullable = false, updatable = false)
    private BigDecimal price;

    @Column(name = "currency", nullable = false, updatable = false)
    private String currency;

}
