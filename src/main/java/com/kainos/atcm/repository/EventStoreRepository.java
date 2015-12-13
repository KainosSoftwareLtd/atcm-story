package com.kainos.atcm.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class EventStoreRepository {
    private HashMap<UUID, String> datastore = new HashMap<>();

    public void storeEvent(UUID correlationId, String json) {
        if (datastore.containsKey(correlationId)) {
            throw new IllegalStateException("I've seen this event before, I have it");
        }
        datastore.put(correlationId, json);
    }
}
