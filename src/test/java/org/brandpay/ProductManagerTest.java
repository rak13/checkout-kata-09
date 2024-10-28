package org.brandpay;

import org.brandpay.pricescheme.PriceScheme;
import org.brandpay.pricescheme.PriceSchemeFactory;
import org.brandpay.product.DefaultProduct; // Assuming DefaultProduct is the concrete implementation of Product
import org.brandpay.product.Product;
import org.brandpay.product.ProductFactory;
import org.brandpay.productmanager.ProductManager;
import org.brandpay.productmanager.ProductManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {

    private ProductManager productManager;

    @BeforeEach
    void setUp() {
        productManager = new ProductManagerImpl();
    }

    @Test
    void testCreateProduct() {
        String productId = "PROD001";

        // Create a product and check it exists
        productManager.createProduct(productId);
        Product product = productManager.getProduct(productId);

        assertNotNull(product, "Product should be created and retrievable.");
        assertEquals(productId, product.getProductId(), "Product ID should match the one provided.");
    }

    @Test
    void testCreateProduct_DuplicateId() {
        String productId = "PROD001";

        // Create product once
        productManager.createProduct(productId);
        Product product = productManager.getProduct(productId);
        assertNotNull(product, "Product should be created on first call.");

        // Attempt to create the same product again
        productManager.createProduct(productId);
        Product duplicateProduct = productManager.getProduct(productId);

        assertNotNull(duplicateProduct, "Product should still exist after trying to create duplicate.");
    }

    @Test
    void testDeleteProduct() {
        String productId = "PROD001";

        // Create a product
        productManager.createProduct(productId);
        Product product = productManager.getProduct(productId);
        assertNotNull(product, "Product should be created.");

        // Delete the product
        productManager.deleteProduct(productId);
        Product deletedProduct = productManager.getProduct(productId);

        assertNull(deletedProduct, "Product should be null after deletion.");
    }

    @Test
    void testPutPriceScheme() {
        String productId = "PROD001";
        productManager.createProduct(productId);

        productManager.putPriceScheme(productId, 5, 100.0);
        Product product = productManager.getProduct(productId);

        assertNotNull(product, "Product should exist.");
        assertEquals(100.0, product.calculatePrice(5), "Calculated price should match the price scheme added.");
    }

    @Test
    void testPutPriceScheme_NonExistentProduct() {
        productManager.putPriceScheme("INVALID_ID", 5, 100.0);

        Product product = productManager.getProduct("INVALID_ID");
        assertNull(product, "Product should not exist.");
    }
}
