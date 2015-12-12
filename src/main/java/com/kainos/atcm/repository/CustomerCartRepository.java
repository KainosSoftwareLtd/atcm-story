package com.kainos.atcm.repository;

import com.kainos.atcm.read.cart.CustomerCart;
import jersey.repackaged.com.google.common.collect.ListMultimap;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rory80hz on 12/12/2015.
 */
public class CustomerCartRepository {
    private ListMultimap<UUID,CustomerCart> dataStore;

    public CustomerCart getCustomerCart(UUID customerCardId){
        List<CustomerCart> cartHistory = dataStore.get(customerCardId);
        Collections.sort(cartHistory, (cart1,cart2)-> cart1.getUpdatedAt().compareTo(cart2.getUpdatedAt()));
        return cartHistory.get(0);
    }
}
