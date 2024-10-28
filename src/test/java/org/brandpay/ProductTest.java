package org.brandpay;

import org.brandpay.pricescheme.PriceScheme;
import org.brandpay.product.DefaultProduct;
import org.brandpay.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductTest {

    private DefaultProduct product;
    private PriceScheme mockScheme1;
    private PriceScheme mockScheme2;
    private String productId = "PROD001";

    @BeforeEach
    void setUp() {
        product = new DefaultProduct(productId);

        // Create mock PriceSchemes
        mockScheme1 = mock(PriceScheme.class);
        mockScheme2 = mock(PriceScheme.class);

        // Configure mock behaviors
        when(mockScheme1.getQuantity()).thenReturn(1);
        when(mockScheme1.getPrice()).thenReturn(100.0);

        when(mockScheme2.getQuantity()).thenReturn(10);
        when(mockScheme2.getPrice()).thenReturn(200.0);
    }

    @Test
    void testGetProductId() {
        assertEquals("PROD001", product.getProductId(), "Product ID should match the one provided in the constructor.");
    }

    @Test
    void testPutAndGetPriceSchemes() {
        product.putPriceScheme(mockScheme1);
        product.putPriceScheme(mockScheme2);

        List<PriceScheme> priceSchemes = product.getPriceSchemes();
        assertEquals(2, priceSchemes.size(), "Price schemes list should contain two elements.");
        assertTrue(priceSchemes.contains(mockScheme1), "Price schemes list should contain mockScheme1.");
        assertTrue(priceSchemes.contains(mockScheme2), "Price schemes list should contain mockScheme2.");
    }

    @Test
    void testCalculatePrice_ExactMatch() {
        product.putPriceScheme(mockScheme1);  // Scheme for quantity 5 at price 100.0
        product.putPriceScheme(mockScheme2);  // Scheme for quantity 10 at price 180.0

        double priceFor1 = product.calculatePrice(1);
        assertEquals(priceFor1, priceFor1, 0.001, "Calculated price for quantity 5 should match scheme price.");

        double priceFor10 = product.calculatePrice(10);
        assertEquals(200.0, priceFor10, 0.001, "Calculated price for quantity 10 should match scheme price.");
    }

    @Test
    void testCalculatePrice_MultipleSchemes() {
        product.putPriceScheme(mockScheme1);  // Scheme for quantity 5 at price 100.0
        product.putPriceScheme(mockScheme2);  // Scheme for quantity 10 at price 200.0

        double priceFor15 = product.calculatePrice(15);
        assertEquals(700.0, priceFor15, 0.001, "Calculated price for quantity 15 should combine schemes (10 + 5).");
    }

    @Test
    void testCalculatePrice_NoMatchingScheme() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> product.calculatePrice(5),
                "Should throw IllegalStateException when no price schemes are available.");
        assertEquals("No price schemes available for calculation.", exception.getMessage());

        //add a scheme
        product.putPriceScheme(mockScheme2);

        exception = assertThrows(IllegalStateException.class,
                () -> product.calculatePrice(5),
                "Should throw IllegalStateException min price scheme is not found");
        assertEquals("No price available for this quantity: " + 5 +", for product: " + productId, exception.getMessage());
    }
}
