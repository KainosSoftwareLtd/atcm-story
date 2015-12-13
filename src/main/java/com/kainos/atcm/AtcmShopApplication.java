package com.kainos.atcm;

import com.kainos.atcm.read.cart.CustomerCart;
import com.kainos.atcm.read.customer.Customer;
import com.kainos.atcm.read.product.Product;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.repository.CustomerRepository;
import com.kainos.atcm.repository.EventStoreRepository;
import com.kainos.atcm.repository.ProductRepository;
import com.kainos.atcm.write.command.AddProductToCustomerCart;
import com.kainos.atcm.write.command.RemoveProductFromCustomerCart;
import com.kainos.atcm.write.event.ProductAddedToCustomerCart;
import com.kainos.atcm.write.handler.ProductAddedToCustomerCartHandler;
import com.kainos.atcm.write.handler.ProductRemovedFromCustomerCartHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AtcmShopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<AtcmShopApplication> applicationClass = AtcmShopApplication.class;
}

@RestController
@RequestMapping("/customer/read")
class CustomerReadController {
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/{customerId}")
    Customer cart(@PathVariable String customerId) {
        Optional<Customer> thing = customerRepository.getCustomer(UUID.fromString(customerId));
        if (!thing.isPresent()) {
            throw new HTTPException(404);
        }
        return thing.get();
    }
}

@RestController
@RequestMapping("/cart/read")
class CustomerCartReadController {
    @Autowired
    CustomerCartRepository customerCartRepository;

    @RequestMapping("/{cartId}")
    CustomerCart cart(@PathVariable String cartId) {
        Optional<CustomerCart> thing = customerCartRepository.getCustomerCart(UUID.fromString(cartId));
        if (!thing.isPresent()) {
            throw new HTTPException(404);
        }
        return thing.get();
    }
}

@RestController
@RequestMapping("/cart/write")
class CustomerCartWriteController {
    @Autowired
    CustomerCartRepository customerCartRepository;

    @Autowired
    EventStoreRepository eventStoreRepository;

    @Autowired
    ProductAddedToCustomerCartHandler productAddedToCustomerCartHandler;

    @Autowired
    ProductRemovedFromCustomerCartHandler productRemovedFromCustomerCartHandler;

    @RequestMapping(value = "/{cartId}", method = RequestMethod.POST)
    Response add(@PathVariable String cartId, @RequestBody AddProductToCustomerCart addProductToCustomerCart) {
        Optional<CustomerCart> thing = customerCartRepository.getCustomerCart(UUID.fromString(cartId));
        if (!thing.isPresent()) {
            throw new HTTPException(404);
        }

        // Store / Publish Event
        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        eventStoreRepository.storeEvent(UUID.randomUUID(), productAddedToCustomerCart.toString());
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        return Response.accepted().build();
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
    Response remove(@PathVariable String cartId, @RequestBody RemoveProductFromCustomerCart removeProductFromCustomerCart) {
        Optional<CustomerCart> thing = customerCartRepository.getCustomerCart(UUID.fromString(cartId));
        if (!thing.isPresent()) {
            throw new HTTPException(404);
        }

        // Store / Publish Event
        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        eventStoreRepository.storeEvent(UUID.randomUUID(), productAddedToCustomerCart.toString());
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        return Response.accepted().build();
    }
}

@RestController
@RequestMapping("/product/read")
class ProductReadController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping()
    Collection<Product> product() {
        return productRepository.getProducts();
    }
}