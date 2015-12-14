package com.kainos.atcm.handler;

import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.event.ProductAddedToCustomerCart;
import com.kainos.atcm.event.ProductRemovedFromCustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Rory80hz on 14/12/2015.
 */
public class ProductRemovedFromCustomerCartHandlerTest {

    private void addProductToCart(UUID cartId, UUID productId, CustomerCartRepository customerCartRepository) {
        UUID corellationId = UUID.randomUUID();
        DateTime dateUpdated = DateTime.now();

        ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler = new ProductAddedToCustomerCartHandler(customerCartRepository);

        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setProductId(productId);
        productAddedToCustomerCart.setCorrelationId(corellationId);
        productAddedToCustomerCart.setUpdateDateTime(dateUpdated);

        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);
    }

    @Test
    public void RemoveOneProductWhenOneExists() throws Exception {
        // Arrange
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID correlationId = UUID.randomUUID();
        DateTime dateUpdated = DateTime.now();

        addProductToCart(cartId, productId, customerCartRepository);

        ProductRemovedFromCustomerCartHandler productRemovedFromCustomerCartHandler = new ProductRemovedFromCustomerCartHandler(customerCartRepository);

        ProductRemovedFromCustomerCart productRemovedFromCustomerCart = new ProductRemovedFromCustomerCart();
        productRemovedFromCustomerCart.setCartId(cartId);
        productRemovedFromCustomerCart.setProductId(productId);
        productRemovedFromCustomerCart.setCorrelationId(correlationId);
        productRemovedFromCustomerCart.setUpdateDateTime(dateUpdated);


        // Act
        productRemovedFromCustomerCartHandler.handle(productRemovedFromCustomerCart);


        // Assert
        Optional<CustomerCart> optionalCustomerCart = customerCartRepository.getLatestCustomerCart(cartId);

        // Assert
        Assert.assertTrue(optionalCustomerCart.isPresent());


        CustomerCart customerCart = optionalCustomerCart.get();
        Assert.assertTrue("Cart Contains One Product", customerCart.getProducts().size() == 0);
    }
}