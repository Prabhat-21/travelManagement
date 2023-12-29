package com.travel.management.entities;

import lombok.Data;

@Data
public class OrderActivityMapping {

    private String id;
    private String orderId;
    private String activityId;

    public OrderActivityMapping(String id, String orderId, String activityId) {
        this.id = id;
        this.orderId = orderId;
        this.activityId = activityId;
    }
}
