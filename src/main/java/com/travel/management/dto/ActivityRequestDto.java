package com.travel.management.dto;

import lombok.Data;

@Data
public class ActivityRequestDto {

    private String name;
    private String description;
    private int cost;
    private int capacity;
    private String destinationId;
}
