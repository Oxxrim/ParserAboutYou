package com.scalors.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Offer {

    private String name;
    private String brand;
    private String color;
    private BigDecimal price;
    private BigDecimal initialPrice;

}
