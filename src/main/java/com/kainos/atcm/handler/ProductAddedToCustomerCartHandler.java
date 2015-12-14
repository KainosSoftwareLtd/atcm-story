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
        CustomerCart customerCart;
        Optional<CustomerCart> cart = customerCartRepository.getLatestCustomerCart(productAddedToCustomerCart.getCartId());

        if(cart.isPresent()){
            customerCart = cart.get();
        }
        else{
            customerCart = new CustomerCart();
            customerCart.setCustomerCartId(productAddedToCustomerCart.getCartId());
        }

        // Sort out the addition of a product
        // Clone Last good state of the cart to prep for Add
        CustomerCart newCustomerCart = cloneCart(productAddedToCustomerCart, customerCart);

        // Deal with Product Addition
        CartProduct productToAdd = getCartProduct(productAddedToCustomerCart, newCustomerCart);

        // Remove existing version (if it exists) and add new version
        newCustomerCart.getProducts().removeIf(p -> p.getProductId().equals(productAddedToCustomerCart.getProductId()));
        newCustomerCart.getProducts().add(productToAdd);

        // Store
        customerCartRepository.storeCustomerCart(newCustomerCart);
    }

    private CustomerCart cloneCart(ProductAddedToCustomerCart productAddedToCustomerCart, CustomerCart customerCart) {
        CustomerCart newCustomerCart = new CustomerCart();
        newCustomerCart.setCorrelationId(productAddedToCustomerCart.getCorrelationId());
        newCustomerCart.setCustomerCartId(productAddedToCustomerCart.getCartId());
        newCustomerCart.setUpdatedAt(productAddedToCustomerCart.getUpdateDateTime());
        newCustomerCart.setProducts(customerCart.getProducts());
        return newCustomerCart;
    }

    private CartProduct getCartProduct(ProductAddedToCustomerCart productAddedToCustomerCart, CustomerCart customerCart) {
        Optional<CartProduct> cartProduct = customerCart.getProducts().stream().filter(p -> p.getProductId() == productAddedToCustomerCart.getProductId()).findFirst();
        CartProduct productToAdd;
        if(cartProduct.isPresent()){
            productToAdd = cartProduct.get();
            int newQuantity = productToAdd.getQuantity() + 1;
            productToAdd.setQuantity(newQuantity);
        }
        else {
            productToAdd = new CartProduct();
            productToAdd.setProductId(productAddedToCustomerCart.getProductId());
            productToAdd.setQuantity(1);
        }

        return productToAdd;
    }
}
