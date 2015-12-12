package com.kainos.atcm.repository;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Rory80hz on 12/12/2015.
 */
public class EventStoreRepository {
    private HashMap<UUID, String> datastore;

    public void storeEvent(UUID correlationId, String json) {
        if (datastore.containsKey(correlationId)) {
            throw new IllegalStateException("I've seen this event before, I have it");
        }
        datastore.put(correlationId, json);
    }
}
