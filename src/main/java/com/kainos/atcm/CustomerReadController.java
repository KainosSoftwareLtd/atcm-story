package com.kainos.atcm;

import com.kainos.atcm.read.customer.Customer;
import com.kainos.atcm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.http.HTTPException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/customer/read")
public class CustomerReadController {
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
