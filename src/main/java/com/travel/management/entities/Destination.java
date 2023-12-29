package com.travel.management.entities;

import lombok.Data;

@Data
public class Destination {
    private String id;
    private String name;
    public Destination(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
