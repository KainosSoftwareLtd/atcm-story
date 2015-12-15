package com.kainos.atcm.handler;

import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.event.ProductAddedToCustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.repository.ProductRepository;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class ProductAddedToCustomerCartHandlerTest {

    private UUID cartId;
    private UUID productId;
    private CustomerCartRepository customerCartRepository;
    private ProductRepository productRepository;
    private ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler;
    private ProductAddedToCustomerCart productAddedToCustomerCart;

    @Before
    public void testSetup(){
        cartId = UUID.randomUUID();
        productId = UUID.randomUUID();

        customerCartRepository = new CustomerCartRepository();
        productRepository = new ProductRepository();
        productAddedToCustomerCartHandler = new ProductAddedToCustomerCartHandler(customerCartRepository, productRepository);
        productAddedToCustomerCart = new ProductAddedToCustomerCart();
    }

    @Test
    public void testAddSingleNewProduct() throws Exception {
        addProductToCustomerCart();

        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        Assert.assertTrue("Cart Should Contain One Product", customerCart.getProducts().size() == 1);
        Assert.assertTrue("Cart Should Contain Correct Product", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Should Contain Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(1));
    }

    @Test
    public void testAddMultipleNewProducts() throws Exception {
        UUID product2Id = UUID.randomUUID();

        addProductToCustomerCart();
        // Second Addition
        productAddedToCustomerCart.setProductId(product2Id);
        productAddedToCustomerCart.setCorrelationId(UUID.randomUUID());
        productAddedToCustomerCart.setUpdateDateTime(DateTime.now());
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        Assert.assertTrue("Cart Should Contain Two Products", customerCart.getProducts().size() == 2);
        Assert.assertTrue("Cart Should Contain Correct Product 1", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Should Contain Correct Product 1 Count", customerCart.getProducts().get(0).getQuantity().equals(1));

        Assert.assertTrue("Cart Should Contain Correct Product 2", customerCart.getProducts().get(1).getProductId() == product2Id);
        Assert.assertTrue("Cart Should Contain Correct Product 2 Count", customerCart.getProducts().get(1).getQuantity().equals(1));
    }

    @Test
    public void testAddMultipleOfTheSameProduct() throws Exception {
        addProductToCustomerCart();

        // Second Addition
        productAddedToCustomerCart.setCorrelationId(UUID.randomUUID());
        productAddedToCustomerCart.setUpdateDateTime(DateTime.now());
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        Assert.assertTrue("Cart Should Contain One Product", customerCart.getProducts().size() == 1);
        Assert.assertTrue("Cart Should Contain Correct Product", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Should Contain Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(2));
    }

    @Test
    public void testAddToNonExistentCart() throws Exception {
        addProductToCustomerCart();

        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);

        Assert.assertTrue("Cart Should Contain One Product", customerCart.getProducts().size() == 1);
        Assert.assertTrue("Cart Should Contain Correct Product", customerCart.getProducts().get(0).getProductId() == productId);
        Assert.assertTrue("Cart Should Contain Correct Product Count", customerCart.getProducts().get(0).getQuantity().equals(1));
    }

    @Test
    public void testAddNonValidProduct () throws Exception {
        //TODO: is it possible to create non valid (with wrong/empty id) product ?
    }


    private void addProductToCustomerCart() {
        UUID correlationId = UUID.randomUUID();
        DateTime dateUpdated = DateTime.now();

        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setProductId(productId);
        productAddedToCustomerCart.setCorrelationId(correlationId);
        productAddedToCustomerCart.setUpdateDateTime(dateUpdated);
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);
    }
}