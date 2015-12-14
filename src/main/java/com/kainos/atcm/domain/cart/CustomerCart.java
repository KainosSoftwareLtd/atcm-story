package com.kainos.atcm.domain.cart;

import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public class CustomerCart {
    private UUID customerCartId;
    private List<CartProduct> products;
    private DateTime updatedAt;
    private UUID correlationId;

    public UUID getCustomerCartId() {
        return customerCartId;
    }

    public void setCustomerCartId(UUID customerCartId) {
        this.customerCartId = customerCartId;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }
}