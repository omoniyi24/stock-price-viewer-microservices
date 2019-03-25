package com.jayjav.stock.stockservice.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Quote {

    private BigDecimal price;
    private String quote;

    public Quote(String quote, BigDecimal price) {
        this.quote = quote;
        this.price = price;
    }
}
