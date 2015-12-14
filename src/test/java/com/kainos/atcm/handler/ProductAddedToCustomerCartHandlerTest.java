package com.kainos.atcm.handler;

import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.event.ProductAddedToCustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ProductAddedToCustomerCartHandlerTest {

    @Test
    public void HandleAddNewProduct() throws Exception {
        // Arrange
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID corellationId = UUID.randomUUID();
        DateTime dateUpdated = DateTime.now();
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();

        ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler = new ProductAddedToCustomerCartHandler(customerCartRepository);

        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setProductId(productId);
        productAddedToCustomerCart.setCorrelationId(corellationId);
        productAddedToCustomerCart.setUpdateDateTime(dateUpdated);

        // Act
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);
        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        // Assert
        Assert.assertTrue("Cart Contains One Product", customerCart.getProducts().size() == 1);
        Assert.assertTrue("Cart Contains Correct Product", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Contains Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(1));
    }

    @Test
    public void HandleAddMultipleProducts() throws Exception {
        // Arrange
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID product2Id = UUID.randomUUID();
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();

        ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler = new ProductAddedToCustomerCartHandler(customerCartRepository);

        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setProductId(productId);

        // Act
        // First Addition
        productAddedToCustomerCart.setCorrelationId(UUID.randomUUID());
        productAddedToCustomerCart.setUpdateDateTime(DateTime.now());
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        // Second Addition
        productAddedToCustomerCart.setProductId(product2Id);
        productAddedToCustomerCart.setCorrelationId(UUID.randomUUID());
        productAddedToCustomerCart.setUpdateDateTime(DateTime.now());
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        // Assert
        Assert.assertTrue("Cart Contains One Product", customerCart.getProducts().size() == 2);
        Assert.assertTrue("Cart Contains Correct Product", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Contains Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(1));

        Assert.assertTrue("Cart Contains Correct Product", customerCart.getProducts().get(1).getProductId() == product2Id);
        Assert.assertTrue("Cart Contains Correct Product Count", customerCart.getProducts().get(1).getQuantity().equals(1));
    }

    @Test
    public void HandleAddMultipleOfTheSameProduct() throws Exception {
        // Arrange
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();

        ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler = new ProductAddedToCustomerCartHandler(customerCartRepository);

        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setProductId(productId);

        // Act
        // First Addition
        productAddedToCustomerCart.setCorrelationId(UUID.randomUUID());
        productAddedToCustomerCart.setUpdateDateTime(DateTime.now());
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        // Second Addition
        productAddedToCustomerCart.setCorrelationId(UUID.randomUUID());
        productAddedToCustomerCart.setUpdateDateTime(DateTime.now());
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        // Assert
        Assert.assertTrue("Cart Contains One Product", customerCart.getProducts().size() == 1);
        Assert.assertTrue("Cart Contains Correct Product", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Contains Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(2));
    }

    @Test
    public void HandleAddToNonExistentCart() throws Exception {
        // Arrange
        UUID cartId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID corellationId = UUID.randomUUID();
        DateTime dateUpdated = DateTime.now();
        CustomerCartRepository customerCartRepository = new CustomerCartRepository();

        ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler = new ProductAddedToCustomerCartHandler(customerCartRepository);

        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setProductId(productId);
        productAddedToCustomerCart.setCorrelationId(corellationId);
        productAddedToCustomerCart.setUpdateDateTime(dateUpdated);

        // Act
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);
        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        // Assert
        Assert.assertTrue("Cart Contains One Product", customerCart.getProducts().size() == 1);
        Assert.assertTrue("Cart Contains Correct Product", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Contains Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(1));
    }
}