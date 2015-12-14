package com.kainos.atcm.repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.kainos.atcm.domain.cart.CustomerCart;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerCartRepository {
    private ListMultimap<UUID, CustomerCart> dataStore;

    public CustomerCartRepository() {
        dataStore = ArrayListMultimap.create();
        CustomerCart testCart = new CustomerCart();
        testCart.setCustomerCartId(UUID.fromString("e78bb203-26c4-40f5-856a-c65e4598a1cd"));
        testCart.setUpdatedAt(DateTime.now());
        dataStore.put(testCart.getCustomerCartId(), testCart);
    }

    public Optional<CustomerCart> getLatestCustomerCart(UUID customerCardId) {
        List<CustomerCart> cartHistory = dataStore.get(customerCardId);
        Collections.sort(cartHistory, (cart1, cart2) -> cart1.getUpdatedAt().compareTo(cart2.getUpdatedAt()));
        return cartHistory.stream().findFirst();
    }

    public void storeCustomerCart(CustomerCart customerCart){
        dataStore.put(customerCart.getCustomerCartId(), customerCart);
    }
}
