package com.kainos.atcm.event;

import org.joda.time.DateTime;

import java.util.UUID;

public class ProductRemovedFromCustomerCart {
    private UUID cartId;
    private UUID productId;
    private UUID correlationId;
    private DateTime updateDateTime;

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }

    public DateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(DateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
