package org.brandpay.pricescheme;

public class PriceSchemeFactory {
    public static PriceScheme createPriceSchema(int quantity, Double price) {
        return new DefaultPriceScheme(quantity, price);
    }
}
