package com.jayjav.stock.frontend.model;

import lombok.Data;

@Data
public class GetQuote {

    private String userName;
    private String quote;
    private Double price;

//    public Quote(String quote, BigDecimal price) {
//        this.quote = quote;
//        this.price = price;
//    }
}
