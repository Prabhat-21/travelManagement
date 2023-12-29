package com.travel.management.entities;

import lombok.Data;

@Data
public class TravelPackage {
    private String id;
    private String name;
    private int capacity;
    private int cost;

    private int enrolled;

    public TravelPackage(String id, String name, int capacity, int cost) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.cost = cost;
        this.enrolled=0;
    }
}
