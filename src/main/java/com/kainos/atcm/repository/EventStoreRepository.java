package com.kainos.atcm.repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Component
public class EventStoreRepository {
    private ListMultimap<UUID, String> customerCartEventStore = ArrayListMultimap.create();
    private ListMultimap<UUID, String> productEventStore = ArrayListMultimap.create();
    private ListMultimap<UUID, String> customerEventStore = ArrayListMultimap.create();

    public void storeCustomerCartEvent(UUID customerCartId,UUID correlationId, String eventRepresentation) {
        // Check for event
        if (customerCartEventStore.values().stream().filter(e->e.contains(correlationId.toString())).findAny().isPresent()){
            throw new IllegalStateException();
        }

        // Put
        customerCartEventStore.put(customerCartId,eventRepresentation);
    }

    public Collection<Map.Entry<UUID, String>> getCustomerCartEvents(){
        return this.customerCartEventStore.entries();
    }

    public void storeProductEvent(UUID productId,UUID correlationId, String eventRepresentation) {
        // Check for event
        if (productEventStore.values().stream().filter(e->e.contains(correlationId.toString())).findAny().isPresent()){
            throw new IllegalStateException();
        }

        // Put
        productEventStore.put(productId,eventRepresentation);
    }

    public Collection<Map.Entry<UUID, String>> getProductEvents(){
        return this.productEventStore.entries();
    }

    public void storeCustomerEvent(UUID customerId, UUID correlationId, String eventRepresentation) {
        // Check for event
        if (customerEventStore.values().stream().filter(e->e.contains(correlationId.toString())).findAny().isPresent()){
            throw new IllegalStateException();
        }

        // Put
        customerEventStore.put(customerId,eventRepresentation);
    }

    public Collection<Map.Entry<UUID, String>> getCustomerEvents(){
        return this.customerEventStore.entries();
    }
}
