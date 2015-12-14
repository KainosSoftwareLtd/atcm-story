package com.kainos.atcm.write.command;

import org.joda.time.DateTime;

import java.util.UUID;

public class RemoveProductFromCustomerCart {
    UUID productId;
    UUID correlationId;
    DateTime updateDateTime;

    public DateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(DateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
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
}
