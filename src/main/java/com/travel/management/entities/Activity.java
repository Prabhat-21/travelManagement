package com.travel.management.entities;

import lombok.Data;

@Data
public class Activity {
    private String id;
    private String name;
    private String description;
    private int cost;
    private int capacity;
    private String destinationId;

    private int remainingCapacity;

    public Activity(String id, String name, String description, int cost, int capacity, String destinationId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.remainingCapacity = capacity;
        this.destinationId = destinationId;
    }

}
