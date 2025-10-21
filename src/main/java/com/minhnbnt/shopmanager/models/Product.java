package com.minhnbnt.shopmanager.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {
    private int id;
    private String name;
    private String producer;
    private String type;
    private String description;
    private int price;
}
