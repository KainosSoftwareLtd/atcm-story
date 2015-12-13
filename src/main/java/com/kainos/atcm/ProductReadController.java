package com.kainos.atcm;

import com.kainos.atcm.read.product.Product;
import com.kainos.atcm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/product/read")
public class ProductReadController {
    @Autowired
    ProductRepository productRepository;

    @RequestMapping()
    Collection<Product> product() {
        return productRepository.getProducts();
    }
}
