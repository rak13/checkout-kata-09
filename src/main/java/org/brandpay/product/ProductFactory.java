package org.brandpay.product;

public class ProductFactory {
    public static Product createProduct(String Id) {
        return new DefaultProduct(Id);
    }
}
