package com.kainos.atcm.write.handler;

import com.kainos.atcm.write.event.ProductAddedToCustomerCart;
import org.springframework.stereotype.Component;

@Component
public class ProductAddedToCustomerCartHandler {
    public void handle(ProductAddedToCustomerCart productAddedToCustomerCart) {
        return;
    }
}
