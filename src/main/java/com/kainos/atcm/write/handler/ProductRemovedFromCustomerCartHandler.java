package com.kainos.atcm.write.handler;

import com.kainos.atcm.write.event.ProductRemovedFromCustomerCart;
import org.springframework.stereotype.Component;

@Component
public class ProductRemovedFromCustomerCartHandler {
    public void handle(ProductRemovedFromCustomerCart productRemovedFromCustomerCart) {
        return;
    }
}
