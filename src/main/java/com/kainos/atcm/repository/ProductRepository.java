package com.kainos.atcm.repository;

import com.kainos.atcm.read.product.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Rory80hz on 12/12/2015.
 */
public class ProductRepository {
    private HashMap<UUID, Product> dataStore;

    public Collection<Product> getProducts() {
        return dataStore.values();
    }

    public Product getProduct(UUID productId) {
        return dataStore.get(productId);
    }

    public List<Product> getProductByName(String productName) {
        Collection<Product> products = dataStore.values();
        List<Product> filtered = products.stream().filter(p -> p.getName().contains(productName)).collect(Collectors.toList());
        return filtered;
    }
}
