package org.brandpay;

import org.brandpay.pricescheme.DefaultPriceScheme;
import org.brandpay.pricescheme.PriceScheme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceSchemeTest {


    private PriceScheme priceScheme;

    @BeforeEach
    void setUp() {
        priceScheme = new DefaultPriceScheme(5, 100.0);
    }

    @Test
    void testGetQuantity() {
        int expectedQuantity = 5;
        assertEquals(expectedQuantity, priceScheme.getQuantity(), "The quantity should match the constructor input.");
    }

    @Test
    void testGetPrice() {
        double expectedPrice = 100.0;
        assertEquals(expectedPrice, priceScheme.getPrice(), "The price should match the constructor input.");
    }
}