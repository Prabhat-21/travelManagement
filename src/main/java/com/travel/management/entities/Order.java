package com.travel.management.entities;

import lombok.Data;

@Data
public class Order {
    private String id;
    private String packageId;
    private String passengerId;

    public Order(String id, String packageId, String passengerId) {
        this.id = id;
        this.packageId = packageId;
        this.passengerId = passengerId;
    }


}
