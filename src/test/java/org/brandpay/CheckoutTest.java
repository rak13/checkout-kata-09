package org.brandpay;

import org.brandpay.checkout.Checkout;
import org.brandpay.checkout.CheckoutImpl;
import org.brandpay.productmanager.ProductManager;
import org.brandpay.productmanager.ProductManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    private ProductManager productManager;
    private Checkout checkout;

    @BeforeEach
    void setUp() {
        productManager = new ProductManagerImpl();
        checkout = new CheckoutImpl(productManager);
    }

    @Test
    void testScanProduct() {
        String productId = "PROD001";

        // Create a product and add a price scheme for testing
        productManager.createProduct(productId);
        productManager.putPriceScheme(productId, 1, 50.0); // Price for 1 item
        productManager.putPriceScheme(productId, 3, 140.0); // Price for 3 items

        checkout.scan(productId);
        assertEquals(50.0, checkout.getTotalPrice(), 0.001, "Total price should reflect the price of the scanned product.");

        checkout.scan(productId);
        assertEquals(100.0, checkout.getTotalPrice(), 0.001, "Total price should reflect the price after scanning the product twice.");

        checkout.scan(productId);
        assertEquals(140.0, checkout.getTotalPrice(), 0.001, "Total price should reflect the price for three items scanned.");

        checkout.scan(productId);
        assertEquals(190.0, checkout.getTotalPrice(), 0.001, "Total price should reflect the price for four items scanned.");
    }

    @Test
    void testDisplayCart() {
        String productId = "PROD001";
        productManager.createProduct(productId);
        productManager.putPriceScheme(productId, 1, 50.0);
        productManager.putPriceScheme(productId, 3, 140.0);

        // Scan the product multiple times
        checkout.scan(productId);
        checkout.scan(productId);
        checkout.scan(productId);

        // Redirect standard output to capture display output for testing
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Display the cart
        checkout.displayCart();

        // Restore original output
        System.setOut(originalOut);

        String expectedOutput = String.format("%-15s | %-10s | %-10s%n", "ProductId", "Quantity", "Price");

        String output = outContent.toString();
//        System.out.println(output);
        assertTrue(output.contains(expectedOutput), "Cart display output should match the expected format and content.");

        expectedOutput = String.format("%-15s | %-10d | %-10.2f%n", productId, 3, 140.0);
        assertTrue(output.contains(expectedOutput), "Cart display output should match the expected format and content.");
    }


    @Test
    void testScanMultipleProducts() {
        String productIdA = "PROD001";
        String productIdB = "PROD002";

        // Set up product A with price schemes
        productManager.createProduct(productIdA);
        productManager.putPriceScheme(productIdA, 1, 50.0); // Price for 1 item
        productManager.putPriceScheme(productIdA, 3, 140.0); // Price for 3 items

        // Set up product B with price schemes
        productManager.createProduct(productIdB);
        productManager.putPriceScheme(productIdB, 1, 90.0); // Price for 2 items
        productManager.putPriceScheme(productIdB, 5, 210.0); // Price for 5 items

        // Scan product A
        checkout.scan(productIdA);
        assertEquals(50.0, checkout.getTotalPrice(), 0.001, "Total price should reflect the price of product A scanned once.");

        // Scan product B
        checkout.scan(productIdB);
        assertEquals(50.0 + 90, checkout.getTotalPrice(), 0.001, "Total price should reflect the price after scanning product B.");

        // Scan product A again
        checkout.scan(productIdA);
        assertEquals(100.0 + 90.0, checkout.getTotalPrice(), 0.001, "Total price should reflect the price after scanning product A again.");

        // Scan product B again
        checkout.scan(productIdB);
        assertEquals(100.0 + 180, checkout.getTotalPrice(), 0.001, "Total price should reflect the price for scanning product B again.");

        checkout.scan(productIdA);
        assertEquals(140 + 180, checkout.getTotalPrice(), 0.001, "Total price should reflect the price after scanning product A again.");
    }



    @Test
    void testScan_ProductNotFound() {
        String productId = "INVALID_ID";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> checkout.scan(productId),
                "Should throw IllegalStateException when scanning a product not found.");

        assertEquals("Product not found: INVALID_ID", exception.getMessage());
    }
}
