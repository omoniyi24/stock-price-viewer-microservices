package com.jayjav.stock.frontend.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Quote {

    private String userName;
    private String quote;

//    public Quote(String quote, BigDecimal price) {
//        this.quote = quote;
//        this.price = price;
//    }
}
