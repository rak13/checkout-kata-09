package org.brandpay.product;

import org.brandpay.pricescheme.PriceScheme;

import java.util.*;

public class AbstractProduct implements Product {
    private final String productId;

    //quantity to priceScheme
    private final TreeMap<Integer, PriceScheme> priceSchemeMap;

    public AbstractProduct(String productId) {
        this.productId = productId;
        priceSchemeMap = new TreeMap<>();
    }

    public String getProductId() {
        return productId;
    }

    public List<PriceScheme> getPriceSchemes() {
        return new ArrayList<>(priceSchemeMap.values());
    }

    @Override
    public void putPriceScheme(PriceScheme priceScheme) {
        priceSchemeMap.put(priceScheme.getQuantity(), priceScheme);
    }

    @Override
    public double calculatePrice(int quantity) {
        if (priceSchemeMap.isEmpty()) {
            throw new IllegalStateException("No price schemes available for calculation.");
        }

        //use schemes
        double price = 0;
        while(quantity != 0) {
            Integer nearestKey = priceSchemeMap.floorKey(quantity);
            if (nearestKey == null) {
                throw new IllegalStateException("No price available for this quantity: " + quantity  + ", for product: " + productId);
            }
            int timesSchemeApplied =  quantity / nearestKey;
            price += (double)timesSchemeApplied * priceSchemeMap.get(nearestKey).getPrice();

            quantity %= nearestKey;
        }
        return price;
    }
}
