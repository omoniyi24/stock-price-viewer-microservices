package com.jayjav.stock.dbservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Quotes {

    private String userName;
    private List<String> quotes;
}
