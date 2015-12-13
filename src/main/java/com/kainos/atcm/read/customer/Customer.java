package com.kainos.atcm.read.customer;

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Rory80hz on 12/12/2015.
 */

public class Customer {
    private UUID customerId;
    private Currency customerCurrency;
    private Locale locale;
    private String name;
    private String address;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Currency getCustomerCurrency() {
        return customerCurrency;
    }

    public void setCustomerCurrency(Currency customerCurrency) {
        this.customerCurrency = customerCurrency;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
