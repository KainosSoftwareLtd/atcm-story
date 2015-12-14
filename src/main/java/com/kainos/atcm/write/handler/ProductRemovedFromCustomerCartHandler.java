package com.kainos.atcm.write.handler;

import com.kainos.atcm.domain.cart.CartProduct;
import com.kainos.atcm.domain.cart.CustomerCart;
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
        Optional<CustomerCart> cart = customerCartRepository.getLatestCustomerCart(productRemovedFromCustomerCart.getCartId());

        // Create cart if it doesn't exist
        if (!cart.isPresent()) {
            throw new IllegalStateException("This cart is not here, this is sad times");
        }

        CustomerCart customerCart = cart.get();

        // Sort out the removal of a product
        // Clone Last good state of the cart to prep for removal
        CustomerCart newCustomerCart = new CustomerCart();
        newCustomerCart.setCorrelationId(productRemovedFromCustomerCart.getCorrelationId());
        newCustomerCart.setCustomerCartId(productRemovedFromCustomerCart.getCartId());
        newCustomerCart.setUpdatedAt(productRemovedFromCustomerCart.getUpdateDateTime());
        newCustomerCart.setProducts(customerCart.getProducts());


        // Find it
        Optional<CartProduct> cartProduct = customerCart.getProducts().stream().filter(p -> p.getId() == productRemovedFromCustomerCart.getProductId()).findFirst();
        CartProduct productToRemove;
        if (cartProduct.isPresent()) {
            productToRemove = cartProduct.get();
            // Reduce quantity by one
            productToRemove.setQuantity(productToRemove.getQuantity() - 1);

            // Remove from the list
            newCustomerCart.getProducts().removeIf(p -> p.getId() == productRemovedFromCustomerCart.getProductId());
            // If we have more than one left we add to our list
            if (productToRemove.getQuantity() > 0) {
                newCustomerCart.getProducts().add(productToRemove);
            }
        }

        // Store
        customerCartRepository.storeCustomerCart(newCustomerCart);
    }
}
