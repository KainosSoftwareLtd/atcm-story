package com.kainos.atcm.repository;

import com.kainos.atcm.domain.cart.CustomerCart;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Component
public class CustomerCartRepository {
    private HashMap<UUID, CustomerCart> customerCartStore = new HashMap<>();

    public Collection<CustomerCart> getAllCarts() {
        return customerCartStore.values();
    }

    public CustomerCart getCustomerCart(UUID customerCardId) {
        return customerCartStore.get(customerCardId);
    }

    public void storeCustomerCart(CustomerCart customerCart) {
        customerCartStore.put(customerCart.getCustomerCartId(), customerCart);
    }
}
