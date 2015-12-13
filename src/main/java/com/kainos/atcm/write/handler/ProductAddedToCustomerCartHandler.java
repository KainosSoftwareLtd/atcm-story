package com.kainos.atcm.write.handler;

import com.kainos.atcm.read.cart.CustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.write.event.ProductAddedToCustomerCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductAddedToCustomerCartHandler {
    @Autowired
    CustomerCartRepository customerCartRepository;

    public void handle(ProductAddedToCustomerCart productAddedToCustomerCart) {
        Optional<CustomerCart> cart = customerCartRepository.getCustomerCart(productAddedToCustomerCart.getCartId());

        // Create cart if it doesn't exist

        // Add product if it isn't there
        //
        // Increment product if it is
        //
        // Store
    }
}
