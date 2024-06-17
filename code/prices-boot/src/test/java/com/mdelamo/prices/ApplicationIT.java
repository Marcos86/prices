package com.mdelamo.prices;

import com.mdelamo.prices.api.rest.PriceQueryRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ApplicationIT {

    @Autowired
    private PriceQueryRestController priceQueryRestController;

    @Test
    void loadsUp() {
        assertNotNull(this.priceQueryRestController);
    }
}