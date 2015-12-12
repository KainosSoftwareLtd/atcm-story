package com.kainos.atcm.repository;

import com.kainos.atcm.read.customer.Customer;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Rory80hz on 12/12/2015.
 */
public class CustomerRepository {
    private HashMap<UUID, Customer> dataStore;

    public Customer getCustomer(UUID customerId) {
        return dataStore.get(customerId);
    }
}
