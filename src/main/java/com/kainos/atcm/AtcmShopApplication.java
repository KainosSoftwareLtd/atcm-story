package com.kainos.atcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
class CustomerReadController {

    @RequestMapping("/read/{customerid}")
    String cart(@PathVariable UUID customerId) {
        return "Hello, " + customerId + "!";
    }
}

@RestController
class CustomerCartReadController {

    @RequestMapping("/read/{cart}")
    String cart(@PathVariable UUID cartId) {
        return "Hello, " + cartId + "!";
    }
}

@RestController
class CustomerCartWriteController {

    @RequestMapping("/write/{cart}")
    String add(@PathVariable UUID cartId) {
        return "Hello, " + cartId + "!";
    }
}

@RestController
class ProductReadController {

    @RequestMapping("/read/products/")
    String product() {
        return "product!";
    }
}