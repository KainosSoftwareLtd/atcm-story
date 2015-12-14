package com.kainos.atcm;

import com.kainos.atcm.domain.cart.CustomerCart;
import com.kainos.atcm.repository.CustomerCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.http.HTTPException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cart/read")
public class CustomerCartReadController {
    @Autowired
    CustomerCartRepository customerCartRepository;

    @RequestMapping("/{cartId}")
    CustomerCart cart(@PathVariable String cartId) {
        Optional<CustomerCart> customerCart = customerCartRepository.getLatestCustomerCart(UUID.fromString(cartId));
        if (!customerCart.isPresent()) {
            throw new HTTPException(404);
        }
        return customerCart.get();
    }
}
