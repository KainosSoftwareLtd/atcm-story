package com.kainos.atcm.handler;

import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.event.ProductAddedToCustomerCart;
import com.kainos.atcm.event.ProductRemovedFromCustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.repository.ProductRepository;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class ProductRemovedFromCustomerCartHandlerTest {

    private void addProductToCart(UUID cartId, UUID productId, CustomerCartRepository customerCartRepository) {
        UUID correlationId = UUID.randomUUID();
        DateTime dateUpdated = DateTime.now();

        ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler = new ProductAddedToCustomerCartHandler(customerCartRepository, new ProductRepository());

        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setProductId(productId);
        productAddedToCustomerCart.setCorrelationId(correlationId);
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
        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        // Assert
        Assert.assertTrue("Cart Contains One Product", customerCart.getProducts().size() == 0);
    }

    @Test
    public void RemoveOneProductWhenTwoExist() throws Exception {
        // Arrange
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID product2Id = UUID.randomUUID();
        UUID correlationId = UUID.randomUUID();
        DateTime dateUpdated = DateTime.now();

        addProductToCart(cartId, productId, customerCartRepository);
        addProductToCart(cartId, product2Id, customerCartRepository);

        ProductRemovedFromCustomerCartHandler productRemovedFromCustomerCartHandler = new ProductRemovedFromCustomerCartHandler(customerCartRepository);

        ProductRemovedFromCustomerCart productRemovedFromCustomerCart = new ProductRemovedFromCustomerCart();
        productRemovedFromCustomerCart.setCartId(cartId);
        productRemovedFromCustomerCart.setProductId(productId);
        productRemovedFromCustomerCart.setCorrelationId(correlationId);
        productRemovedFromCustomerCart.setUpdateDateTime(dateUpdated);


        // Act
        productRemovedFromCustomerCartHandler.handle(productRemovedFromCustomerCart);
        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        // Assert
        Assert.assertTrue("Cart Contains One Product", customerCart.getProducts().size() == 1);
        Assert.assertTrue("Cart Contains Correct Product", customerCart.getProducts().get(0).getProductId() == product2Id);
        Assert.assertTrue("Cart Contains Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(1));
    }

    @Test
    public void RemoveOneProductWhenTwoOfTheSameExist() throws Exception {
        // Arrange
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID correlationId = UUID.randomUUID();
        DateTime dateUpdated = DateTime.now();

        addProductToCart(cartId, productId, customerCartRepository);
        addProductToCart(cartId, productId, customerCartRepository);

        ProductRemovedFromCustomerCartHandler productRemovedFromCustomerCartHandler = new ProductRemovedFromCustomerCartHandler(customerCartRepository);

        ProductRemovedFromCustomerCart productRemovedFromCustomerCart = new ProductRemovedFromCustomerCart();
        productRemovedFromCustomerCart.setCartId(cartId);
        productRemovedFromCustomerCart.setProductId(productId);
        productRemovedFromCustomerCart.setCorrelationId(correlationId);
        productRemovedFromCustomerCart.setUpdateDateTime(dateUpdated);


        // Act
        productRemovedFromCustomerCartHandler.handle(productRemovedFromCustomerCart);
        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        // Assert
        Assert.assertTrue("Cart Contains One Product", customerCart.getProducts().size() == 1);
        Assert.assertTrue("Cart Contains Correct Product", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Contains Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(1));
    }
}