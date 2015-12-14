package com.kainos.atcm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/uniqueId")
public class UniqueIdController {
    @RequestMapping()
    UUID correlationId() {
        return UUID.randomUUID();
    }
}
