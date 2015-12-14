package com.kainos.atcm.write.handler;

import com.kainos.atcm.domain.cart.CartProduct;
import com.kainos.atcm.domain.cart.CustomerCart;
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
        Optional<CustomerCart> cart = customerCartRepository.getLatestCustomerCart(productAddedToCustomerCart.getCartId());

        if(!cart.isPresent()){
            throw new IllegalStateException("This cart is not here, this is sad times");
        }

        CustomerCart customerCart = cart.get();

        // Sort out the addition of a product
        // Clone Last good state of the cart to prep for Add
        CustomerCart newCustomerCart = new CustomerCart();
        newCustomerCart.setCorrelationId(productAddedToCustomerCart.getCorrelationId());
        newCustomerCart.setCustomerCartId(productAddedToCustomerCart.getCartId());
        newCustomerCart.setUpdatedAt(productAddedToCustomerCart.getUpdateDateTime());
        newCustomerCart.setProducts(customerCart.getProducts());

        Optional<CartProduct> cartProduct = customerCart.getProducts() .stream().filter(p -> p.getId() == productAddedToCustomerCart.getProductId()).findFirst();
        CartProduct productToAdd;
        if(!cartProduct.isPresent()){
            productToAdd = new CartProduct();
            productToAdd.setId(productAddedToCustomerCart.getProductId());
        }
        else {
            productToAdd = cartProduct.get();
            productToAdd.setQuantity(productToAdd.getQuantity() + 1);
        }

        // Remove existing version and add new version (if it exists)
        newCustomerCart.getProducts().removeIf(p -> p.getId() == productAddedToCustomerCart.getProductId());
        newCustomerCart.getProducts().add(productToAdd);

        // Store
        customerCartRepository.storeCustomerCart(newCustomerCart);
    }
}
