package com.kainos.atcm.write.handler;

import com.kainos.atcm.read.cart.CustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.write.event.ProductRemovedFromCustomerCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductRemovedFromCustomerCartHandler {
    @Autowired
    CustomerCartRepository customerCartRepository;

    public void handle(ProductRemovedFromCustomerCart productRemovedFromCustomerCart) {
        Optional<CustomerCart> cart = customerCartRepository.getCustomerCart(productRemovedFromCustomerCart.getCartId());

        // This is broke, Exception time.

        // No Product? Uh oh. Well no negative product count so...i guess ok?
        //
        // Decrement product if it is here...if zero, remove it all together
        //
        // Store
    }
}
