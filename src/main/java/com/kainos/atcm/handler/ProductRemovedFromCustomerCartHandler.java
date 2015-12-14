package com.kainos.atcm.handler;

import com.kainos.atcm.domain.cart.CartProduct;
import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.event.ProductRemovedFromCustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductRemovedFromCustomerCartHandler {
    private CustomerCartRepository customerCartRepository;

    @Autowired
    public ProductRemovedFromCustomerCartHandler(CustomerCartRepository customerCartRepository) {
        this.customerCartRepository = customerCartRepository;
    }

    public void handle(ProductRemovedFromCustomerCart productRemovedFromCustomerCart) {
        CustomerCart customerCart = customerCartRepository.getCustomerCart(productRemovedFromCustomerCart.getCartId());

        // Create cart if it doesn't exist
        if (customerCart == null) {
            throw new IllegalStateException("This cart is not here, this is sad times");
        }

        // Find it
        Optional<CartProduct> cartProduct = customerCart.getProducts().stream().filter(p -> p.getProductId().equals(productRemovedFromCustomerCart.getProductId())).findFirst();
        CartProduct productToRemove;
        if (cartProduct.isPresent()) {
            productToRemove = cartProduct.get();
            // Reduce quantity by one
            int newQuantity = productToRemove.getQuantity() - 1;
            productToRemove.setQuantity(newQuantity);

            // Remove from the list
            customerCart.getProducts().removeIf(p -> p.getProductId().equals(productRemovedFromCustomerCart.getProductId()));
            // If we have more than one left we add to our list
            if (productToRemove.getQuantity() > 0) {
                customerCart.getProducts().add(productToRemove);
            }
        }

        customerCart.setCorrelationId(productRemovedFromCustomerCart.getCorrelationId());
        customerCart.setUpdatedAt(productRemovedFromCustomerCart.getUpdateDateTime());

        // Store
        customerCartRepository.storeCustomerCart(customerCart);
    }
}
