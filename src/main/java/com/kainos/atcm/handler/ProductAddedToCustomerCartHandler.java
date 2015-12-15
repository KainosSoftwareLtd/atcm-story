package com.kainos.atcm.handler;

import com.kainos.atcm.domain.cart.CartProduct;
import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.domain.product.Product;
import com.kainos.atcm.event.ProductAddedToCustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ProductAddedToCustomerCartHandler {

    private CustomerCartRepository customerCartRepository;

    private ProductRepository productRepository;

    @Autowired
    public ProductAddedToCustomerCartHandler(CustomerCartRepository customerCartRepository, ProductRepository productRepository) {
        this.customerCartRepository = customerCartRepository;
        this.productRepository = productRepository;
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
        UUID productId = productAddedToCustomerCart.getProductId();
        Optional<CartProduct> cartProduct = customerCart.getProducts().stream().filter(p -> p.getProductId().equals(productId)).findFirst();
        CartProduct productToAdd;
        if (cartProduct.isPresent()) {
            productToAdd = cartProduct.get();
            int newQuantity = productToAdd.getQuantity() + 1;
            productToAdd.setQuantity(newQuantity);
        } else {
            productToAdd = new CartProduct();
            productToAdd.setProductId(productId);
            productToAdd.setQuantity(1);
        }

        Product productDeatils = this.productRepository.getProduct(productId);

        if(productDeatils!=null){
            productToAdd.setDescription(productDeatils.getDescription());
            productToAdd.setCost(productDeatils.getCost());
            productToAdd.setName(productDeatils.getName());
        }

        return productToAdd;
    }
}
