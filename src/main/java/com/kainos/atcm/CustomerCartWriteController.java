package com.kainos.atcm;

import com.kainos.atcm.read.cart.CustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.repository.EventStoreRepository;
import com.kainos.atcm.write.command.AddProductToCustomerCart;
import com.kainos.atcm.write.command.RemoveProductFromCustomerCart;
import com.kainos.atcm.write.event.ProductAddedToCustomerCart;
import com.kainos.atcm.write.event.ProductRemovedFromCustomerCart;
import com.kainos.atcm.write.handler.ProductAddedToCustomerCartHandler;
import com.kainos.atcm.write.handler.ProductRemovedFromCustomerCartHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cart/write")
public class CustomerCartWriteController {
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
        ProductRemovedFromCustomerCart productRemovedFromCustomerCart = new ProductRemovedFromCustomerCart();
        eventStoreRepository.storeEvent(UUID.randomUUID(), productRemovedFromCustomerCart.toString());
        productRemovedFromCustomerCartHandler.handle(productRemovedFromCustomerCart);

        return Response.accepted().build();
    }
}
