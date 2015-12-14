package com.kainos.atcm.handler;

import com.kainos.atcm.domain.cart.CartProduct;
import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.event.ProductAddedToCustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductAddedToCustomerCartHandler {

    private CustomerCartRepository customerCartRepository;

    @Autowired
    public ProductAddedToCustomerCartHandler(CustomerCartRepository customerCartRepository) {
        this.customerCartRepository = customerCartRepository;
    }

    public void handle(ProductAddedToCustomerCart productAddedToCustomerCart) {
        CustomerCart customerCart = customerCartRepository.getCustomerCart(productAddedToCustomerCart.getCartId());

        if (customerCart == null) {
            customerCart = new CustomerCart();
            customerCart.setCustomerCartId(productAddedToCustomerCart.getCartId());
        }

        // Deal with Product Addition
        CartProduct productToAdd = getCartProduct(productAddedToCustomerCart, customerCart);

        // Remove existing version (if it exists) and add new version
        customerCart.getProducts().removeIf(p -> p.getProductId().equals(productAddedToCustomerCart.getProductId()));
        customerCart.getProducts().add(productToAdd);

        customerCart.setCorrelationId(productAddedToCustomerCart.getCorrelationId());
        customerCart.setUpdatedAt(productAddedToCustomerCart.getUpdateDateTime());

        // Store
        customerCartRepository.storeCustomerCart(customerCart);
    }

    private CartProduct getCartProduct(ProductAddedToCustomerCart productAddedToCustomerCart, CustomerCart customerCart) {
        Optional<CartProduct> cartProduct = customerCart.getProducts().stream().filter(p -> p.getProductId().equals(productAddedToCustomerCart.getProductId())).findFirst();
        CartProduct productToAdd;
        if (cartProduct.isPresent()) {
            productToAdd = cartProduct.get();
            int newQuantity = productToAdd.getQuantity() + 1;
            productToAdd.setQuantity(newQuantity);
        } else {
            productToAdd = new CartProduct();
            productToAdd.setProductId(productAddedToCustomerCart.getProductId());
            productToAdd.setQuantity(1);
        }

        return productToAdd;
    }
}
