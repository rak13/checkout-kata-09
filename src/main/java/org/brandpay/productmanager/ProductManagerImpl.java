package org.brandpay.productmanager;

import org.brandpay.pricescheme.PriceSchemeFactory;
import org.brandpay.product.Product;
import org.brandpay.product.ProductFactory;

import java.util.HashMap;
import java.util.Map;

public class ProductManagerImpl implements ProductManager {
    private final Map<String, Product> productMap;

    public ProductManagerImpl() {
        productMap = new HashMap<>();
    }

    @Override
    public void createProduct(String productId) {
        if(productMap.containsKey(productId)) {
            return;
        }
        productMap.put(productId, ProductFactory.createProduct(productId));
    }

    @Override
    public void deleteProduct(String productId) {
        productMap.remove(productId);
    }

    @Override
    public void putPriceScheme(String productId, int quantity, double price) {
        Product product = productMap.get(productId);
        if(product == null) {
            return;
        }
        product.putPriceScheme(PriceSchemeFactory.createPriceSchema(quantity, price));
    }

    @Override
    public Product getProduct(String productId) {
        return productMap.get(productId);
    }
}
