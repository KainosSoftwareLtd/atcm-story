package com.kainos.atcm.write.command;

import org.joda.time.DateTime;

import java.util.UUID;

public class RemoveProductFromCustomerCart {
    UUID productId;
    UUID correlationId;
    DateTime updateDateTime;
}
