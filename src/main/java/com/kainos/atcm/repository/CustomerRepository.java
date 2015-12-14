package com.kainos.atcm.repository;

import com.kainos.atcm.domain.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepository {
    private HashMap<UUID, Customer> dataStore = new HashMap<>();

    public CustomerRepository() {
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setName("Test Person");
        dataStore.put(customer.getCustomerId(), customer);
    }

    public Optional<Customer> getCustomer(UUID customerId) {
        return dataStore.values().stream().findFirst();
    }
}
