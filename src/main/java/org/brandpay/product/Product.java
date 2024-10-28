package org.brandpay.product;

import org.brandpay.pricescheme.PriceScheme;

public interface Product {
    String getProductId();
    void putPriceScheme(PriceScheme priceScheme);
    double calculatePrice(int quantity);
}
