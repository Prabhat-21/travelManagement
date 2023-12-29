package com.travel.management.dto;

import lombok.Data;

@Data
public class PackageRequestDto {
    private String name;
    private int capacity;
    private int baseCost;
}
