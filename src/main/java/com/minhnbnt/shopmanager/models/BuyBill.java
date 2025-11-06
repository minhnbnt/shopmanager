package com.minhnbnt.shopmanager.models;

import jakarta.annotation.Nullable;
import java.sql.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BuyBill {

    private int id;

    private Customer customer;

    @Nullable
    private SaleAgent submitter;

    @Nullable
    private DeliveryStaff deliveryStaff;

    private Date createOn;

    @Nullable
    private Date receivedOn;

    private double total;

    private List<BuyDetail> details;

    private String status;
}
