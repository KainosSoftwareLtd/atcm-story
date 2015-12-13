package com.kainos.atcm.repository;

import com.kainos.atcm.read.product.Product;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductRepository {
    private HashMap<UUID, Product> dataStore = new HashMap<>();

    public ProductRepository() {

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(UUID.randomUUID());
            product.setCategory("Category " + i);
            product.setCost((float) i);
            product.setName("Product " + i);
            product.setDescription("Description " + i);
            dataStore.put(product.getId(),product);
        }
    }

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
