package org.brandpay.checkout;

public interface Checkout {
    double getTotalPrice();
    void scan(String productId);
    void displayCart();
}
