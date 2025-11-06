package com.minhnbnt.shopmanager.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BuyDetail {

    private int id;
    private int quantity;
    private double price;
    private Product product;
    private double subTotal;
}
