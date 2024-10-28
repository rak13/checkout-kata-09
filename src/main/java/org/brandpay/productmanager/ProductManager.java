package org.brandpay.productmanager;

import org.brandpay.product.Product;

public interface ProductManager {
    public void createProduct(String productId);
    public void deleteProduct(String productId);
    public void putPriceScheme(String productId, int quantity, double price);

    public Product getProduct(String productId);
}
