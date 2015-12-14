package com.kainos.atcm.command;

import java.util.UUID;

public class RemoveProductFromCustomerCart {
    UUID productId;
    UUID correlationId;
    String updateDateTimeUTC;

    public String getUpdateDateTimeUTC() {
        return updateDateTimeUTC;
    }

    public void setUpdateDateTimeUTC(String updateDateTimeUTC) {
        this.updateDateTimeUTC = updateDateTimeUTC;
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
