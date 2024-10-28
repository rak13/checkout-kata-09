package org.brandpay.pricescheme;

public abstract class AbstractPriceScheme implements PriceScheme {
    private final int quantity;
    private final double price;

    public AbstractPriceScheme(int quantity, Double price) {
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
