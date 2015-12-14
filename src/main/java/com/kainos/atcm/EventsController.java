package com.kainos.atcm;

import com.kainos.atcm.repository.EventStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/events/read")
public class EventsController {
    @Autowired
    EventStoreRepository eventStoreRepository;

    @RequestMapping("/customer-cart}")
    Collection<Map.Entry<UUID, String>> customerCartEvents() {
        return eventStoreRepository.getCustomerCartEvents();
    }
}