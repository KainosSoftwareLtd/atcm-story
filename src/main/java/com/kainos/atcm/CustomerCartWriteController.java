package com.kainos.atcm;

import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import com.kainos.atcm.repository.EventStoreRepository;
import com.kainos.atcm.command.AddProductToCustomerCart;
import com.kainos.atcm.command.RemoveProductFromCustomerCart;
import com.kainos.atcm.event.ProductAddedToCustomerCart;
import com.kainos.atcm.event.ProductRemovedFromCustomerCart;
import com.kainos.atcm.handler.ProductAddedToCustomerCartHandler;
import com.kainos.atcm.handler.ProductRemovedFromCustomerCartHandler;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;
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
    Response add(@PathVariable UUID cartId, @RequestBody AddProductToCustomerCart addProductToCustomerCart) {
        // Validation could go here
        //

        // Map Command to Event
        //
        ProductAddedToCustomerCart productAddedToCustomerCart = new ProductAddedToCustomerCart();
        productAddedToCustomerCart.setCartId(cartId);
        productAddedToCustomerCart.setCorrelationId(addProductToCustomerCart.getCorrelationId());
        productAddedToCustomerCart.setProductId(addProductToCustomerCart.getProductId());

        // Date Time Antics
        //
        productAddedToCustomerCart.setUpdateDateTime(DateTime.parse(addProductToCustomerCart.getUpdateDateTimeUTC()));

        // Store Event
        //
        eventStoreRepository.storeCustomerCartEvent(cartId, productAddedToCustomerCart.getCorrelationId(), productAddedToCustomerCart.toString());

        // Publish Event (Queue goes here)
        //
        productAddedToCustomerCartHandler.handle(productAddedToCustomerCart);

        return Response.accepted().build();
    }

    @RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
    Response remove(@PathVariable UUID cartId, @RequestBody RemoveProductFromCustomerCart removeProductFromCustomerCart) {
        CustomerCart customerCart = customerCartRepository.getCustomerCart(cartId);
        if (customerCart == null) {
            throw new HTTPException(404);
        }

        // Map Command to Event
        ProductRemovedFromCustomerCart productRemovedFromCustomerCart = new ProductRemovedFromCustomerCart();
        productRemovedFromCustomerCart.setCartId(cartId);
        productRemovedFromCustomerCart.setCorrelationId(removeProductFromCustomerCart.getCorrelationId());
        productRemovedFromCustomerCart.setProductId(removeProductFromCustomerCart.getProductId());

        // Date Time Antics
        productRemovedFromCustomerCart.setUpdateDateTime(DateTime.parse(removeProductFromCustomerCart.getUpdateDateTimeUTC()));

        // Store Event
        eventStoreRepository.storeCustomerCartEvent(cartId, productRemovedFromCustomerCart.getCorrelationId(), productRemovedFromCustomerCart.toString());

        // Publish Event (Queue goes here)
        productRemovedFromCustomerCartHandler.handle(productRemovedFromCustomerCart);

        return Response.accepted().build();
    }
}
