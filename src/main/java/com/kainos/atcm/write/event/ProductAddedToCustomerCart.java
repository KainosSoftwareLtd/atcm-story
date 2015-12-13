package com.kainos.atcm.write.event;

import org.joda.time.DateTime;

import java.util.UUID;

public class ProductAddedToCustomerCart {
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

    public UUID getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public DateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(DateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
