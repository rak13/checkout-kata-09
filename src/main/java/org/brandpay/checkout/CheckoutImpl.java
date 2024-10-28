package org.brandpay.checkout;

import org.brandpay.product.Product;
import org.brandpay.productmanager.ProductManager;

import java.util.HashMap;
import java.util.Map;

public class CheckoutImpl implements Checkout {
    private final ProductManager productManager;

    private final Map<String, Integer> scannedItems;
    private final Map<String, Double> productPrices;

    private double totalPrice = 0;

    public CheckoutImpl(ProductManager productManager) {
        this.productManager = productManager;
        this.totalPrice = 0;
        scannedItems = new HashMap<>();
        productPrices = new HashMap<>();
    }

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public void scan(String productId) {
        int count = scannedItems.getOrDefault(productId, 0);
        scannedItems.put(productId, ++count);
        Product product = productManager.getProduct(productId);
        if(product == null) {
            throw new IllegalStateException("Product not found: " + productId);
        }

        double existingProdPrice = productPrices.getOrDefault(productId, 0.0);
        double curProdPrice = product.calculatePrice(count);

        productPrices.put(productId, curProdPrice);

        totalPrice -= existingProdPrice;
        totalPrice += curProdPrice;
    }

    @Override
    public void displayCart() {
        // Print header with formatted alignment
        System.out.printf("%-15s | %-10s | %-10s%n", "ProductId", "Quantity", "Price");
        System.out.println("---------------------------------------------");

        // Iterate through scanned items and print each row
        for (String productId : scannedItems.keySet()) {
            int quantity = scannedItems.get(productId);
            double price = productPrices.get(productId);

            // Format each row to align columns
            System.out.printf("%-15s | %-10d | %-10.2f%n", productId, quantity, price);
        }
    }
}
